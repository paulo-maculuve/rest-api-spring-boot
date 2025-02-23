package com.maculuve.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maculuve.controllers.docs.PersonControllerDocs;
import com.maculuve.data.dto.v1.PersonDTO;
import com.maculuve.services.PersonService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/person")
@Tag(name = "People", description = "Endpoints for managing People")
public class PersonController implements PersonControllerDocs {

        @Autowired
        private PersonService personService;

        @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_YAML_VALUE })
        @Override
        public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                        @RequestParam(value = "size", defaultValue = "15") Integer size,
                        @RequestParam(value = "direction", defaultValue = "asc") String direction) {

                var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
                Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
                return ResponseEntity.ok(personService.findAll(pageable));
        }

        @GetMapping(value = "/findPeopleByName/{firstName}", produces = { MediaType.APPLICATION_JSON_VALUE,
                        MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_YAML_VALUE })
        @Override
        public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findByName(
                        @PathVariable(value = "firstName") String firstName,
                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                        @RequestParam(value = "size", defaultValue = "15") Integer size,
                        @RequestParam(value = "direction", defaultValue = "asc") String direction) {
                var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
                Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
                return ResponseEntity.ok(personService.findByName(firstName, pageable));
        }

        @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_YAML_VALUE })
        @Override
        public PersonDTO findById(@PathVariable(value = "id") Long id) {
                return personService.findById(id);
        }

        @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_YAML_VALUE }, produces = {
                                        MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
                                        MediaType.APPLICATION_YAML_VALUE })
        @Override
        public PersonDTO store(@RequestBody PersonDTO person) {
                return personService.store(person);
        }

        @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_YAML_VALUE }, produces = {
                                        MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
                                        MediaType.APPLICATION_YAML_VALUE })
        @Override
        public PersonDTO update(@RequestBody PersonDTO person) {
                return personService.update(person);
        }

        @PatchMapping(value = "/{id}", produces = {
                        MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_YAML_VALUE })
        @Override
        public PersonDTO disablePerson(@PathVariable(value = "id") Long id) {
                return personService.disablePerson(id);
        }

        @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
                        MediaType.APPLICATION_XML_VALUE })
        @Override
        public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
                personService.delete(id);
                return ResponseEntity.noContent().build();
        }

}
