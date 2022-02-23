package com.epam.library.feign;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.library.dto.BookDto;

@FeignClient(name="Book-Service")
@LoadBalancerClient(name="Book-Service")
public interface Bookfeign {

	@PostMapping("/books")
	public ResponseEntity<BookDto> addBook(@RequestBody  BookDto bookDto);
	@GetMapping("/books")
	public  ResponseEntity<List<BookDto>> viewAllBooks(); 
	
	@GetMapping("/books/{book_id}")
	public  ResponseEntity<Object> getBookDetailsByBookId( @PathVariable("book_id")int id ); 
	@PutMapping("/books/{book_id}")
	public ResponseEntity<BookDto> updateBookById(@PathVariable("book_id") int  id,
			@RequestBody  BookDto bookDto);
	
	@DeleteMapping("/books/{book_id}")
	public  ResponseEntity<String>  deleteBookById(@PathVariable("book_id")  int id);
	
}
