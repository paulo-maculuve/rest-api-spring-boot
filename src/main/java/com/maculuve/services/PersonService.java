package com.maculuve.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static com.maculuve.mapper.DozerMapper.parseObject;
import static com.maculuve.mapper.DozerMapper.parseListObject;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maculuve.controllers.PersonController;
import com.maculuve.data.dto.v1.PersonDTO;
import com.maculuve.exceptions.RequiredObjectIsNullException;
import com.maculuve.exceptions.ResourceNotFoundException;

import com.maculuve.model.Person;
import com.maculuve.repositories.PersonRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonService {
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    public List<PersonDTO> findAll() {
        var persons = parseListObject(personRepository.findAll(), PersonDTO.class);
        persons.forEach(this::addHateoasLinks);
        return persons;
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
        var entity = personRepository.findById(person.getKey())
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

    private void addHateoasLinks(PersonDTO personDTO) {
        personDTO.add(
                linkTo(methodOn(PersonController.class).findById(personDTO.getKey())).withSelfRel().withType("GET"));
        personDTO.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        personDTO
                .add(linkTo(methodOn(PersonController.class).store(personDTO)).withRel("store").withType("POST"));
        personDTO.add(linkTo(methodOn(PersonController.class).update(personDTO)).withRel("update").withType("PUT"));
        personDTO.add(linkTo(methodOn(PersonController.class).disablePerson(personDTO.getKey())).withRel("disable")
                .withType("PATCH"));
        personDTO.add(linkTo(methodOn(PersonController.class).delete(personDTO.getKey())).withRel("delete")
                .withType("DELETE"));
    }

}
