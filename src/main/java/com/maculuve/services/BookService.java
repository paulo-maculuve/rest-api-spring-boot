package com.maculuve.services;

import static com.maculuve.mapper.DozerMapper.parseListObject;
import static com.maculuve.mapper.DozerMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maculuve.controllers.BookController;
import com.maculuve.data.dto.v1.BookDTO;
import com.maculuve.exceptions.RequiredObjectIsNullException;
import com.maculuve.exceptions.ResourceNotFoundException;
import com.maculuve.model.Book;
import com.maculuve.repositories.BookRepository;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<BookDTO> findAll() {
        var books = parseListObject(bookRepository.findAll(), BookDTO.class);
        books.forEach(this::addHateoasLinks);
        return books;
    }

    public BookDTO findById(Long id) {
        var entity = bookRepository.findById(id)

                .orElseThrow(() -> new ResourceNotFoundException("book Not Found"));

        var dto = parseObject(entity, BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BookDTO store(BookDTO book) {
        if (book == null)
            throw new RequiredObjectIsNullException();
        var entity = parseObject(book, Book.class);
        var dto = parseObject(bookRepository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BookDTO update(BookDTO book) {
        if (book == null)
            throw new RequiredObjectIsNullException();
        var entity = bookRepository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setTitle(book.getTitle());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());

        var dto = parseObject(bookRepository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {

        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        bookRepository.delete(entity);
    }

    private void addHateoasLinks(BookDTO bookDTO) {
        bookDTO.add(linkTo(methodOn(BookController.class).findById(bookDTO.getKey())).withSelfRel().withType("GET"));
        bookDTO.add(linkTo(methodOn(BookController.class).findAll()).withRel("findAll").withType("GET"));
        bookDTO.add(linkTo(methodOn(BookController.class).store(bookDTO)).withRel("store").withType("POST"));
        bookDTO.add(linkTo(methodOn(BookController.class).update(bookDTO)).withRel("update").withType("PUT"));
        bookDTO.add(
                linkTo(methodOn(BookController.class).delete(bookDTO.getKey())).withRel("delete").withType("DELETE"));
    }
}
