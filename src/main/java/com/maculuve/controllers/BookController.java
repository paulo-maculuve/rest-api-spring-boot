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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maculuve.controllers.docs.BookControllerDocs;
import com.maculuve.data.dto.v1.BookDTO;
import com.maculuve.services.BookService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/book")
@Tag(name = "Book", description = "Endpoints for managing Book")
public class BookController implements BookControllerDocs {

        @Autowired
        private BookService bookService;

        @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })

        public ResponseEntity<PagedModel<EntityModel<BookDTO>>> findAll(
                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                        @RequestParam(value = "size", defaultValue = "15") Integer size,
                        @RequestParam(value = "direction", defaultValue = "asc") String direction) {

                var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
                Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
                return ResponseEntity.ok(bookService.findAll(pageable));
        }

        @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })

        public BookDTO findById(@PathVariable(value = "id") Long id) {
                return bookService.findById(id);
        }

        @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
                        MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })

        public BookDTO store(@RequestBody BookDTO bookDTO) {
                return bookService.store(bookDTO);
        }

        @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
                        MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })

        public BookDTO update(@RequestBody BookDTO bookDTO) {
                return bookService.update(bookDTO);
        }

        @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
                        MediaType.APPLICATION_XML_VALUE })

        public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
                bookService.delete(id);
                return ResponseEntity.noContent().build();
        }

}