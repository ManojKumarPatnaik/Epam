package com.epam.library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.library.dto.BookDto;
import com.epam.library.dto.UserDto;
import com.epam.library.feign.Bookfeign;
import com.epam.library.feign.UserFeign;
import com.epam.library.service.FeignService;
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class FeignServiceTest {
	@InjectMocks
	FeignService feignService;
	@MockBean
	UserFeign userFeign;
	@MockBean
	Bookfeign bookFeign;
	
	UserDto userDto;
	List<UserDto> users;
	BookDto book;
	List<BookDto> books;
	
	
	@BeforeEach
	void setUp() {
		userDto = new UserDto("manoj", "Manoj@gmail.com", "manojkumar");
		users = new ArrayList<>();
		users.add(new UserDto( "manoj", "Manoj@gmail.com", "manojkumar"));
		users.add(new UserDto( "ajeet", "Ajeet@gmail.com", "ajeetBank"));
		book = new BookDto(1,"manoj", "ManojPublisher", "manojkumar");
		books = new ArrayList<>();
		books.add(new BookDto(1, "manoj", "ManojPublisher", "manojkumar"));
		books.add(new BookDto( 2,"ajeet", "AjeetPublisher", "ajeetBank"));
			
	}
	
	
	@Test
	void addUserTest() {
		ResponseEntity<UserDto> addUser=new ResponseEntity<>(userDto, HttpStatus.OK);
		when(userFeign.addUser(userDto)).thenReturn(addUser);
		assertEquals(addUser, feignService.addUser(userDto));
	
	
	}
	
	
	
	
	@Test
	void viewAllUsersTest() {
		ResponseEntity<List<UserDto>> response=new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
		when(userFeign.viewAllUsers()).thenReturn(response);
		assertEquals(response, feignService.viewAllUsers());
	
	
	}
	
	@Test
	void getUserByUsernameTest() {
		ResponseEntity<UserDto> response=new ResponseEntity<>(userDto, HttpStatus.OK);
		when(userFeign.getUserByUsername("manoj")).thenReturn(response);
		assertEquals(response, feignService.getUserByUsername("manoj"));
	
	
	}
	
	
	@Test
	void updateUserByUserNameTest() {
		ResponseEntity<UserDto> response=new ResponseEntity<>(userDto, HttpStatus.OK);
		when(userFeign.updateUserByUserName("manoj",userDto)).thenReturn(response);
		assertEquals(response, feignService.updateUserByUserName("manoj",userDto));
	
	
	}
	
	
	@Test
	void deleteUserTest() {
		ResponseEntity<String> response=new ResponseEntity<>("User deleted Successfully", HttpStatus.OK);
		when(userFeign.deleteUser("manoj")).thenReturn(response);
		assertEquals(response, feignService.deleteUser("manoj"));
	
	
	}
	
	
	@Test
	void addBookrTest() {
		ResponseEntity<BookDto> addBook=new ResponseEntity<>(book, HttpStatus.OK);
		when(bookFeign.addBook(book)).thenReturn(addBook);
		assertEquals(addBook, feignService.addBook(book));
	
	
	}
	
	@Test
	void viewAllBooksTest() {
		ResponseEntity<List<BookDto>> response=new ResponseEntity<List<BookDto>>(books, HttpStatus.OK);
		when(bookFeign.viewAllBooks()).thenReturn(response);
		assertEquals(response, feignService.viewAllBooks());
	
	
	}
	
	@Test
	void getBookDetailsByBookIdTest() {
		ResponseEntity<Object> response=new ResponseEntity<>(book, HttpStatus.OK);
		when(bookFeign.getBookDetailsByBookId(1)).thenReturn(response);
		assertEquals(response, feignService.getBookDetailsByBookId(1));
	
	
	}
	
	
	@Test
	void updateBookByIdTest() {
		ResponseEntity<BookDto> response=new ResponseEntity<>(book, HttpStatus.OK);
		when(bookFeign.updateBookById(1,book)).thenReturn(response);
		assertEquals(response, feignService.updateBookById(1,book));
	
	
	}
	
	
	@Test
	void deleteBookByIdTest() {
		ResponseEntity<String> response=new ResponseEntity<>("Book deleted Successfully", HttpStatus.OK);
		when(bookFeign.deleteBookById(1)).thenReturn(response);
		assertEquals(response, feignService.deleteBookById(1));
	
	
	}
	
}
