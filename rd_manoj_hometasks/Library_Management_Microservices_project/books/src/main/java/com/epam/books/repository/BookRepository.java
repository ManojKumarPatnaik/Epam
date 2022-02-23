package com.epam.books.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.books.model.Book;

@Transactional
public interface BookRepository extends JpaRepository<Book, Integer>{


	
	
	
}
