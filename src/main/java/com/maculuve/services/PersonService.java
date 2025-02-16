package com.maculuve.services;

import java.util.List;
import java.util.logging.Logger;

import com.maculuve.exceptions.RequiredObjectIsNullException;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import com.maculuve.controllers.PersonController;
import com.maculuve.data.dto.v1.PersonDTO;
import com.maculuve.exceptions.ResourceNotFoundException;
import com.maculuve.mapper.DozerMapper;
import com.maculuve.model.Person;
import com.maculuve.repositories.PersonRepository;

@Service
public class PersonService {
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    public List<PersonDTO> findAll() {
        var persons = DozerMapper.parseListObject(personRepository.findAll(), PersonDTO.class);
        persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        return persons;
    }

    public PersonDTO findById(Long id) {
        logger.info("Find person by Id");
        var entity = personRepository.findById(id)

                .orElseThrow(() -> new ResourceNotFoundException("Person Not Found"));

                var vo = DozerMapper.parseObject(entity, PersonDTO.class);
                vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonDTO store(PersonDTO person) {
        if(person == null) throw new RequiredObjectIsNullException();
        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(personRepository.save(entity), PersonDTO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonDTO update(PersonDTO person) {
        if(person == null) throw new RequiredObjectIsNullException();
        var entity = personRepository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = DozerMapper.parseObject(personRepository.save(entity), PersonDTO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {

        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        personRepository.delete(entity);
    }

}
