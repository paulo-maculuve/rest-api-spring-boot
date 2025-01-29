package com.maculuve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maculuve.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
