package com.epam.library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.epam.library.dto.LibraryDto;
import com.epam.library.dto.UserDto;
import com.epam.library.exception.BookAlreadyIssuedToThisUserException;
import com.epam.library.exception.RecordNotFoundException;
import com.epam.library.feign.Bookfeign;
import com.epam.library.feign.UserFeign;
import com.epam.library.model.Library;
import com.epam.library.repository.LibraryRepository;
import com.epam.library.service.FeignService;
import com.epam.library.service.LibraryService;


@ExtendWith(MockitoExtension.class)

@ExtendWith(SpringExtension.class)
public class LibraryServiceTest {
	
	
	@InjectMocks
	FeignService feignService;
	@InjectMocks
	LibraryService libraryService;
	@MockBean
	UserFeign userFeign;
	@MockBean
	Bookfeign bookFeign;
	
	@MockBean
	LibraryRepository libraryRepo;
	
	UserDto userDto;
	List<UserDto> users;
	BookDto bookDto;
	List<BookDto> books;
	LibraryDto libraryDto;
	List<Library> libraryDtos;
	Library library;
	
	@BeforeEach
	void setUp() {
		library=new Library(1,"manoj",1);
		userDto = new UserDto("manoj", "Manoj@gmail.com", "manojkumar");
		users = new ArrayList<>();
		users.add(new UserDto( "manoj", "Manoj@gmail.com", "manojkumar"));
		users.add(new UserDto( "ajeet", "Ajeet@gmail.com", "ajeetBank"));
		libraryDtos=new ArrayList<>();
		libraryDtos.add(library);
	}
	
	
	
	@Test
	void returnBookTest()  {
		
		
		doReturn(Optional.of(library)).when(libraryRepo).findByUsernameAndBookId("manoj", 1);
		doNothing().when(libraryRepo).deleteByUsernameAndBookId("manoj",1);
		assertEquals(true, libraryService.returnBook("manoj",1));
	
	
	}
	
	@Test
	void returnBookWithInvalidTest()  {
		
		
		doReturn(Optional.of(library)).when(libraryRepo).findByUsernameAndBookId(null, 8786);
		doNothing().when(libraryRepo).deleteByUsernameAndBookId("2563",1);
		assertThrows(RecordNotFoundException.class,()-> libraryService.returnBook("manoj",1));
	
	
	}
	
	
	@Test
	void findByUsernameTest()  {
		
		
		when(libraryRepo.findByUsername("manoj")).thenReturn(libraryDtos);
		assertEquals(libraryDtos,libraryService.findByUsername("manoj"));
	
	
	}
	
	
	@Test
	void findByUsernameWithInvalidTest()  {
		
		
		when(libraryRepo.findByUsername("65536")).thenReturn(null);
		assertEquals(null,libraryService.findByUsername("65536"));
	
	
	}
	
	
	@Test
	void viewAllBooksByUserNameTest()  {
		
		
		when(libraryRepo.findByUsername("manoj")).thenReturn(libraryDtos);
		assertEquals(libraryDtos,libraryService.viewAllBooksByUserName("manoj"));
	
	
	}
	
	
	@Test
	void viewAllBooksByUserNameWithInvalidTest()  {
		
		
		when(libraryRepo.findByUsername("367623")).thenReturn(null);
		assertThrows(RecordNotFoundException.class,()-> libraryService.viewAllBooksByUserName(null));
		
	
	}
	
	@Test
	void issueBookToUserTest()  {
		
		String username = "manoj";
		int bookId=1;
		ResponseEntity<UserDto> response=new ResponseEntity<>(userDto, HttpStatus.OK);
		ResponseEntity<Object> responseBook=new ResponseEntity<>(bookId, HttpStatus.OK);
		
		when(userFeign.getUserByUsername(username)).thenReturn(response);
		when(bookFeign.getBookDetailsByBookId(bookId)).thenReturn(responseBook);
		
		when(libraryRepo.findByUsername("manoj")).thenReturn(libraryDtos);

		when(libraryRepo.save(new Library(username,bookId))).thenReturn(library);
		assertEquals("Book Issued Successfully to the "+username,libraryService.issueBookToUser(username,bookId));
	
	
	}
	
	@Test
	void issueBookToUserWithInvalidTest()  {
		
		String username = "manoj";
		int bookId=1;
		ResponseEntity<UserDto> response=new ResponseEntity<>(userDto, HttpStatus.OK);
		ResponseEntity<Object> responseBook=new ResponseEntity<>(bookId, HttpStatus.OK);
		
		when(userFeign.getUserByUsername(username)).thenReturn(response);
		when(bookFeign.getBookDetailsByBookId(bookId)).thenReturn(responseBook);
		
		doReturn(Optional.of(library)).when(libraryRepo).findByUsernameAndBookId(username, bookId);
		when(libraryRepo.findByUsername("manoj")).thenReturn(libraryDtos);

		when(libraryRepo.save(new Library(username,bookId))).thenReturn(library);
		assertThrows(BookAlreadyIssuedToThisUserException.class,()->libraryService.issueBookToUser(username,bookId));
	
	
	}

}
