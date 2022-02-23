package com.epam.books.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.books.exception.BookAlreadyExitsException;
import com.epam.books.exception.BookDoesNotExistException;
import com.epam.books.exception.NoRecordsException;
import com.epam.books.model.Book;
import com.epam.books.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService{
		@Autowired
		BookRepository bookRepository;
		
	
	
	@Override
	public Book addBook(Book book) throws BookAlreadyExitsException{
		Optional<Book> bookDetails =bookRepository.findById(book.getId());
		if(bookDetails.isPresent()) {
			  throw new BookAlreadyExitsException("Book Id Already exits,Try New Book Id");
		  }
		return bookRepository.save(book);
	}



	@Override
	public List<Book> viewAllBooks() throws NoRecordsException{
		List<Book> books=bookRepository.findAll();
		if(books.isEmpty()) {
			throw new NoRecordsException("No Record Found. Please Add a book");
		}
		return bookRepository.findAll();
	}



	@Override
	public Book getBookDetailsByBookId(int id) throws BookDoesNotExistException {
		Optional<Book> book=bookRepository.findById(id);
		if(book.isEmpty()) {
			throw new BookDoesNotExistException("No Records present with that book Id");
		}
		return book.get();
	}



	@Override
	public Book updateBookById(Book book) throws BookDoesNotExistException{
		Optional<Book> deleteBook=bookRepository.findById(book.getId());
		if (deleteBook.isEmpty()) {
			throw new BookDoesNotExistException("Invalid Book Id");
		}
		return bookRepository.save(book);
	}



	@Override
	public boolean deleteBookByBookId(int id) throws BookDoesNotExistException{
		Optional<Book> deleteBook=bookRepository.findById(id);
		boolean isDeleted=false;
			if(deleteBook.isEmpty()) {
				throw new BookDoesNotExistException("No Records present with that book Id");
		}else {
			bookRepository.deleteById(id);
			isDeleted=true;
		}
		return isDeleted;
	}

}
