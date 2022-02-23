package com.epam.library.controller;

import java.util.List;

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

import com.epam.library.dto.BookDto;
import com.epam.library.dto.UserDto;
import com.epam.library.model.Library;
import com.epam.library.service.FeignService;
import com.epam.library.service.LibraryService;

@RestController
@RequestMapping("/library")
public class LibraryController {

	@Autowired
	FeignService feignService;
	
	@Autowired
	LibraryService libraryService;
	
	
	
	@PostMapping("/users")
	public ResponseEntity<UserDto> addUser(@RequestBody  UserDto userDto){
		
		return feignService.addUser(userDto);
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> viewAllUsers(){
		return feignService.viewAllUsers();
	}

	
	@GetMapping("/users/{userName}")
	public  ResponseEntity<UserDto> getUserByUsername( @PathVariable("userName")String username ){
		
		return feignService.getUserByUsername(username);
	}
	
	@PutMapping("/users/{userName}")
	public ResponseEntity<UserDto> updateUserByUserName(@PathVariable("userName") String username,
			@RequestBody  UserDto userDto){
		return feignService.updateUserByUserName(username,userDto);
	}
	
	
	@DeleteMapping("/users/{userName}")
	public  ResponseEntity<String>  deleteUser(@PathVariable("userName")  String username){
		return feignService.deleteUser(username);
	}
	
	
	
	@PostMapping("/books")
	public ResponseEntity<BookDto> addBook(@RequestBody  BookDto bookDto){
		return feignService.addBook(bookDto);
	}
	@GetMapping("/books")
	public  ResponseEntity<List<BookDto>> viewAllBooks(){
		return feignService.viewAllBooks();
	}
	
	@GetMapping("/books/{bookId}")
	public  ResponseEntity<Object> getBookDetailsByBookId( @PathVariable("bookId")int id ){
		return feignService.getBookDetailsByBookId(id);
	}
	@PutMapping("/books/{bookId}")
	public ResponseEntity<BookDto> updateBookById(@PathVariable("bookId") int  id,
			@RequestBody  BookDto bookDto){
		return feignService.updateBookById(id,bookDto);
	}
	
	@DeleteMapping("/books/{bookId}")
	public  ResponseEntity<String>  deleteBookById(@PathVariable("bookId")  int id){
		return feignService.deleteBookById(id);
	}
	

	@PostMapping("/users/{username}/books/{bookId}")
	public ResponseEntity<String>  issueBookToUser(@PathVariable  String username,@PathVariable  int bookId){
		
		return new ResponseEntity<> (libraryService.issueBookToUser(username,bookId),HttpStatus.OK);
	}
	
	
	@DeleteMapping("/users/{username}/books/{bookId}")
	public ResponseEntity<String>  returnBook(@PathVariable  String username,@PathVariable  int bookId){
		boolean isDeleted=libraryService.returnBook(username,bookId);
		return new ResponseEntity<> (isDeleted?"book Returned successfully":"Unable to found user",HttpStatus.OK);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<List<Library>>  viewLibraryDetails(@PathVariable  String username){
		List<Library> viewAllBooksByUserName = libraryService.viewAllBooksByUserName(username);
		
		return new ResponseEntity<> (viewAllBooksByUserName,viewAllBooksByUserName.isEmpty()?HttpStatus.BAD_REQUEST: HttpStatus.OK);
	}
	
	
	
}
