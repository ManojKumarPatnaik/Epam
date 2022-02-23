package com.epam.books.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.books.model.Book;

@Service
public interface BookService {
	
	Book addBook(Book book);

	List<Book> viewAllBooks();


	Book updateBookById(Book user);


	Book getBookDetailsByBookId(int id);

	boolean deleteBookByBookId(int id);


}
