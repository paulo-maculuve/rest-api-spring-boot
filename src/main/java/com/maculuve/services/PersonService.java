package com.maculuve.services;

import static com.maculuve.mapper.DozerMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maculuve.controllers.PersonController;
import com.maculuve.data.dto.v1.PersonDTO;
import com.maculuve.exceptions.BadRequestException;
import com.maculuve.exceptions.FileStorageException;
import com.maculuve.exceptions.RequiredObjectIsNullException;
import com.maculuve.exceptions.ResourceNotFoundException;
import com.maculuve.file.exporter.contract.FileExporter;
import com.maculuve.file.exporter.factory.FileExporterFactory;
import com.maculuve.file.importer.contract.FileImporter;
import com.maculuve.file.importer.factory.FileImporterFactory;
import com.maculuve.model.Person;
import com.maculuve.repositories.PersonRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonService {
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private FileImporterFactory fileImporter;
    @Autowired
    private FileExporterFactory fileExporter;

    @SuppressWarnings("rawtypes")
    @Autowired
    private PagedResourcesAssembler assembler;

    public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable) {
        logger.info("Find all Person");
        var people = personRepository.findAll(pageable);
        return getFindAllLink(pageable, people);
    }

    public Resource exportPage(Pageable pageable, String acceptHeader) {
        logger.info("Exporting a People page!");
        var people = personRepository.findAll(pageable)
                .map(person -> parseObject(person, PersonDTO.class))
                .getContent();

        try {
            FileExporter exportFile = this.fileExporter.getExporter(acceptHeader);
            return exportFile.exportFile(people);
        } catch (Exception e) {
            throw new RuntimeException("Error during file export!", e);
        }
    }


    public PagedModel<EntityModel<PersonDTO>> findByName(String firstName, Pageable pageable) {
        logger.info("Find person by firstName");
        var people = personRepository.findPeopleByName(firstName, pageable);
        return getFindAllLink(pageable, people);
    }

    public List<PersonDTO> multCreation(MultipartFile file) {
        logger.info("Importing People from file!");
        if (file.isEmpty())
            throw new BadRequestException("Plase set a valid File!");
        try (InputStream inputStream = file.getInputStream()) {
            String fileName = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(() -> new BadRequestException("File name cannot be null!"));

            FileImporter fileImporter = this.fileImporter.getImporter(fileName);

            List<Person> entities = fileImporter.importFile(inputStream).stream()
                    .map(dto -> personRepository.save(parseObject(dto, Person.class)))
                    .toList();

            return entities.stream()
                    .map(entity -> {
                        var dto = parseObject(entity, PersonDTO.class);
                        addHateoasLinks(dto);
                        return dto;
                    }).toList();

        } catch (Exception e) {
            throw new FileStorageException("Error processing the file " + e.getMessage());
        }

    }

    public PersonDTO findById(Long id) {
        logger.info("Find person by Id");
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person Not Found"));

        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO store(PersonDTO person) {
        if (person == null)
            throw new RequiredObjectIsNullException();
        var entity = parseObject(person, Person.class);
        var dto = parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO update(PersonDTO person) {
        if (person == null)
            throw new RequiredObjectIsNullException();
        var entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional
    public PersonDTO disablePerson(Long id) {

        personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        personRepository.disablePerson(id);

        var entity = personRepository.findById(id).get();
        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {

        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        personRepository.delete(entity);
    }

    //@formatter:off
    private void addHateoasLinks(PersonDTO personDTO) {
        personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getId())).withSelfRel().withType("GET"));
        personDTO.add(linkTo(methodOn(PersonController.class).findAll(0, 15, "asc")).withRel("findAll").withType("GET"));
        personDTO.add(linkTo(methodOn(PersonController.class).findByName("", 0, 15, "asc")).withRel("findByName").withType("GET"));
        personDTO.add(linkTo(methodOn(PersonController.class).store(personDTO)).withRel("store").withType("POST"));
        personDTO.add(linkTo(methodOn(PersonController.class)).slash(personDTO).withRel("multCreation").withType("POST"));
        personDTO.add(linkTo(methodOn(PersonController.class).update(personDTO)).withRel("update").withType("PUT"));
        personDTO.add(linkTo(methodOn(PersonController.class).disablePerson(personDTO.getId())).withRel("disable").withType("PATCH"));
        personDTO.add(linkTo(methodOn(PersonController.class).delete(personDTO.getId())).withRel("delete").withType("DELETE"));
        personDTO.add(linkTo(methodOn(PersonController.class).exportPage(1, 12, "asc", null)).withRel("exportPage").withType("GET").withTitle("Export People")
    );
    }
    //@formatter:on

    @SuppressWarnings("unchecked")
    private PagedModel<EntityModel<PersonDTO>> getFindAllLink(Pageable pageable, Page<Person> people) {
        var peopleWithLinks = people.map(person -> {
            var dto = parseObject(person, PersonDTO.class);
            addHateoasLinks(dto);
            return dto;
        });
        Link findAllLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), String.valueOf(pageable.getSort())))
                .withSelfRel();
        return assembler.toModel(peopleWithLinks, findAllLink);
    }
}
