package com.api.book.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.book.entities.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{

}
 