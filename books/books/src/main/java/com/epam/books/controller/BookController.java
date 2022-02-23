package com.epam.books.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.books.dto.BookDto;
import com.epam.books.model.Book;
import com.epam.books.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@PostMapping("")
	public ResponseEntity<Book> addBook(@RequestBody @Valid BookDto bookDto) 
	{
			
		Book  book =  bookService.addBook(new Book(bookDto.getId(),bookDto.getName(),bookDto.getPublisher(),bookDto.getAuthor()));
		return new ResponseEntity<> (book,book!=null?HttpStatus.OK:HttpStatus.BAD_REQUEST);
	}
	
	
	@GetMapping("")
	public  ResponseEntity<List<Book>> viewAllBooks() 
	{
		List<Book> viewAllBooks = bookService.viewAllBooks();
		return new ResponseEntity<>(viewAllBooks,viewAllBooks!=null?HttpStatus.OK:HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/{book_id}")
	public  ResponseEntity<Book> getBookDetailsByBookId( @PathVariable("book_id")int id ) 
	{
		Book userDetails=bookService.getBookDetailsByBookId(id);
		return new ResponseEntity<>(userDetails,userDetails!=null?HttpStatus.OK:HttpStatus.BAD_REQUEST);
	}
	

	
	@PutMapping("/{book_id}")
	public ResponseEntity<Book> updateBookById(@PathVariable("book_id") int  id,
			@RequestBody @Valid BookDto bookDto) {
		
		 Book	book = bookService.getBookDetailsByBookId(id);
			book = new Book(bookDto.getId(),bookDto.getName(),bookDto.getPublisher(),bookDto.getAuthor());
			book = bookService.updateBookById(book);
		return new ResponseEntity<>( book ,book!=null? HttpStatus.OK:HttpStatus.BAD_REQUEST);
	}
	
	
	@DeleteMapping("/{book_id}")
	public  ResponseEntity<String>  deleteBookById(@PathVariable("book_id")  int id) {
		boolean isDeleted=false;
		if(bookService.deleteBookByBookId(id)) {
			isDeleted=true;
			
		}
		return new ResponseEntity<>(isDeleted?"Book deleted Successfully": "Unable to find the Book Id.", isDeleted? HttpStatus.OK :HttpStatus.NOT_FOUND);
	}
	

}
