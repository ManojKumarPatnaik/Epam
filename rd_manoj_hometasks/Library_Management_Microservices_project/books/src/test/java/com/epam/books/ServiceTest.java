package com.epam.books;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.books.exception.BookAlreadyExitsException;
import com.epam.books.exception.BookDoesNotExistException;
import com.epam.books.exception.NoRecordsException;
import com.epam.books.model.Book;
import com.epam.books.repository.BookRepository;
import com.epam.books.service.BookServiceImpl;


@ExtendWith(MockitoExtension.class)

@ExtendWith(SpringExtension.class)
public class ServiceTest {


	List<Book> books;


	Book book;


	@Mock
	BookRepository bookRepository;

	@InjectMocks
	BookServiceImpl bookService;

	@BeforeEach
	void setUp() {
		book = new Book(1,"manoj", "ManojPublisher", "manojkumar");
		books = new ArrayList<>();
		books.add(new Book(1, "manoj", "ManojPublisher", "manojkumar"));
		books.add(new Book( 2,"ajeet", "AjeetPublisher", "ajeetBank"));
				
	}

	@Test
	void addBookTest() {
			
		book=new Book(3,"kumar", "KumarPublisher", "kumarRao");
		when(bookRepository.save(new Book(3,"kumar", "KumarPublisher", "kumarRao"))).thenReturn(book);
		Assertions.assertEquals(book, bookService.addBook(new Book(3,"kumar", "KumarPublisher", "kumarRao")));

	}
	
	@Test
	void addBookInvalidTest() {
		doReturn(Optional.of(book)).when(bookRepository).findById(1);
		
		Assertions.assertThrows(BookAlreadyExitsException.class,()-> bookService.addBook(book));

	}

	@Test
	void viewAllBooksTest() {

		when(bookRepository.findAll()).thenReturn(books);

		Assertions.assertEquals(books, bookService.viewAllBooks());

	}
	
	@Test
	void viewAllBooksWithInvalidTest() {
		List<Book> books=new ArrayList<>();
		when(bookRepository.findAll()).thenReturn(books);

		Assertions.assertThrows(NoRecordsException.class, ()->bookService.viewAllBooks());

	}
	
	@Test
	void updateBookByIdTest() {
		doReturn(Optional.of(book)).when(bookRepository).findById(1);
		
		when(bookRepository.save(book)).thenReturn(book);

		Assertions.assertEquals(book, bookService.updateBookById(new Book(1,"manoj", "ManojPublisher", "manojkumar")));

	}
	
	@Test
	void updateBookByIdWithInvalidTest() {
		
		Assertions.assertThrows(BookDoesNotExistException.class,()-> bookService.updateBookById( book));


	}
	
	
	@Test
	void deleteBookByBookIdTest() {
		 doReturn(Optional.of(book)).when(bookRepository).findById(1);
		 doNothing().when(bookRepository).deleteById(1);
		Assertions.assertEquals(true, bookService.deleteBookByBookId(1));
		verify(bookRepository,times(1)).deleteById(1);
		   
	}
	
	
	
	@Test
	void deleteBookByBookIdWithInvalidTest() {
		 Assertions.assertThrows(BookDoesNotExistException.class,()-> bookService.deleteBookByBookId(1));
		   
	}
	
	
	@Test
	void getBookDetailsByBookIdTest() {
		 doReturn(Optional.of(book)).when(bookRepository).findById(1);
		Assertions.assertEquals(book,bookService.getBookDetailsByBookId(1));

	}
	
	@Test
	void getBookDetailsByBookIdWithInvalidTest() {
		 Assertions.assertThrows(BookDoesNotExistException.class,()-> bookService.getBookDetailsByBookId(1));

	}
	
}
