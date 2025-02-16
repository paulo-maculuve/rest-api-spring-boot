package com.maculuve.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maculuve.controllers.BookController;
import com.maculuve.data.dto.v1.BookDTO;
import com.maculuve.exceptions.RequiredObjectIsNullException;
import com.maculuve.exceptions.ResourceNotFoundException;
import com.maculuve.mapper.DozerMapper;
import com.maculuve.model.Book;
import com.maculuve.repositories.BookRepository;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<BookDTO> findAll() {
        var books = DozerMapper.parseListObject(bookRepository.findAll(), BookDTO.class);
        books.stream().forEach(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel()));
        return books;
    }

    public BookDTO findById(Long id) {
        var entity = bookRepository.findById(id)

                .orElseThrow(() -> new ResourceNotFoundException("book Not Found"));

                var vo = DozerMapper.parseObject(entity, BookDTO.class);
                vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    public BookDTO store(BookDTO book) {
        if(book == null) throw new RequiredObjectIsNullException();
        var entity = DozerMapper.parseObject(book, Book.class);
        var vo = DozerMapper.parseObject(bookRepository.save(entity), BookDTO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookDTO update(BookDTO book) {
        if(book == null) throw new RequiredObjectIsNullException();
        var entity = bookRepository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setTitle(book.getTitle());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());

        var vo = DozerMapper.parseObject(bookRepository.save(entity), BookDTO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {

        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        bookRepository.delete(entity);
    }

}
