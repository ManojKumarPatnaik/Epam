package com.epam.books;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.books.dto.BookDto;
import com.epam.books.model.Book;
import com.epam.books.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BooksApplicationTests {
	@Autowired
	MockMvc mockMvc;
	String json;
	ObjectMapper objectMapper;
	@MockBean
	BookService bookService;
	@MockBean
	Book book;
	@MockBean
	BookDto bookDto;
	List<Book> books;
	
	@BeforeEach
	void setUp() {
		book = new Book(1,"manoj", "ManojPublisher", "manojkumar");
		books = new ArrayList<>();
		books.add(new Book(1, "manoj", "ManojPublisher", "manojkumar"));
		books.add(new Book( 2,"ajeet", "AjeetPublisher", "ajeetBank"));
		bookDto = new BookDto();
		bookDto.setId(3);
		bookDto.setName("manoj");
		bookDto.setAuthor("ManojKumar");
		bookDto.setPublisher("ManojPublisher");
		objectMapper = new ObjectMapper();

	}

	

	@Test
	void viewBooksTest() throws Exception {
		String uri = "/books";
		when(bookService.viewAllBooks()).thenReturn(books);
		json = objectMapper.writeValueAsString(books);
		mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}
	
	
	
	@Test
	void addBookTest() throws Exception {
		String uri = "/books";
		when(bookService.addBook(book)).thenReturn(book);
		json = objectMapper.writeValueAsString(book);
		mockMvc.perform(post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
				.andExpect(status().isOk());
	}
	
	@Test
	void viewBooksWithInvalidTest() throws Exception {
		String uri = "/books";
		when(bookService.viewAllBooks()).thenReturn(null);
		json = objectMapper.writeValueAsString(books);
		mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void addBookWithInvalidTest() throws Exception {
		String uri = "/books";
		when(bookService.addBook(new Book(3,bookDto.getName(), bookDto.getPublisher(), bookDto.getAuthor()))).thenReturn(null);
		json = objectMapper.writeValueAsString(book);
		mockMvc.perform(post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void getBookDetailsByBookIdTest() throws Exception {
		String uri = "/books/1";
		when(bookService.getBookDetailsByBookId(1)).thenReturn(book);
		json = objectMapper.writeValueAsString(books);
		mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}
	
	@Test
	void getBookDetailsByBookIdWithInvalidTest() throws Exception {
		String uri = "/books/167";
		book=new Book(1,"ajeet", "Ajeetgmail", "ajeetBank");
		when(bookService.getBookDetailsByBookId(167)).thenReturn(null);
		json = objectMapper.writeValueAsString(book);
		mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isBadRequest());
	}
	@Test
	void updateBookByIdTest() throws Exception {
		String uri = "/books/1";
		when(bookService.getBookDetailsByBookId(1)).thenReturn(book);
		book=new Book(1,"ajeet", "Ajeetgmail", "ajeetBank");
		when( bookService.updateBookById(book)).thenReturn(book);
		json = objectMapper.writeValueAsString(book);
		mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}
	
	@Test
	void updateBookByIdWithInvaidTest() throws Exception {
		String uri = "/books/187";
		when(bookService.getBookDetailsByBookId(187)).thenReturn(null);
		book=new Book(1,"ajeet", "Ajeetgmail", "ajeetBank");
		when( bookService.updateBookById(new Book(156,"ajeet", "Ajeetgmail", "ajeetBank"))).thenReturn(book);
		json = objectMapper.writeValueAsString(book);
		mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void deleteBookByBookIdTest() throws Exception {
		String uri = "/books/1";
		when(bookService.deleteBookByBookId(1)).thenReturn(true);
		json = objectMapper.writeValueAsString("Book deleted Successfully");
		mockMvc.perform(delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}

	
	@Test
	void deleteBookByBookIdWithInvalidTest() throws Exception {
		String uri = "/books/167";
		when(bookService.deleteBookByBookId(167)).thenReturn(false);
		json = objectMapper.writeValueAsString("Unable to find the Book Id.");
		mockMvc.perform(delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isNotFound());
	}
}
