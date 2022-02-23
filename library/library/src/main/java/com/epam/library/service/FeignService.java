package com.epam.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.epam.library.dto.BookDto;
import com.epam.library.dto.UserDto;
import com.epam.library.feign.Bookfeign;
import com.epam.library.feign.UserFeign;
import com.epam.library.repository.UserRepository;

@Service
public class FeignService {
	
	
	@Autowired
	UserFeign userFeign;
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Bookfeign bookFeign;

	public ResponseEntity<UserDto> addUser( UserDto userDto) {
		
		return userFeign.addUser(userDto);
	}

	public ResponseEntity<List<UserDto>> viewAllUsers() {
		return userFeign.viewAllUsers();
	}

	public ResponseEntity<UserDto> getUserByUsername(String username) {
		return userFeign.getUserByUsername(username);
	}

	public ResponseEntity<UserDto> updateUserByUserName(String username,UserDto userDto) {
		return userFeign.updateUserByUserName(username, userDto);
	}

	public ResponseEntity<String> deleteUser(String username) {
		return userFeign.deleteUser(username);
	}

	public ResponseEntity<BookDto> addBook(BookDto bookDto) {
		return  bookFeign.addBook(bookDto);
	}

	public ResponseEntity<List<BookDto>> viewAllBooks() {
		return  bookFeign.viewAllBooks();
	}

	public ResponseEntity<Object> getBookDetailsByBookId(int id) {
		return  bookFeign.getBookDetailsByBookId(id);
	}

	public ResponseEntity<BookDto> updateBookById(int id, BookDto bookDto) {
		return  bookFeign.updateBookById(id,bookDto);
	}

	public ResponseEntity<String> deleteBookById(int id) {
		return  bookFeign.deleteBookById(id);
	}

	
	
	
	
}
