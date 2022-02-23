package com.epam.library;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.library.dto.BookDto;
import com.epam.library.dto.LibraryDto;
import com.epam.library.dto.UserDto;
import com.epam.library.model.Library;
import com.epam.library.service.FeignService;
import com.epam.library.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryApplicationTests {

	@Autowired
	MockMvc mockMvc;
	String json;
	ObjectMapper objectMapper;
	@MockBean
	FeignService feignService;
	@MockBean
	LibraryService libraryService;
	UserDto userDto;
	List<UserDto> users;
	BookDto bookDto;
	List<BookDto> books;
	LibraryDto libraryDto;
	List<Library> libraryDtos;
	
	@BeforeEach
	void setUp() {
		userDto = new UserDto("manoj", "Manoj@gmail.com", "manojkumar");
		users = new ArrayList<>();
		users.add(new UserDto( "manoj", "Manoj@gmail.com", "manojkumar"));
		users.add(new UserDto( "ajeet", "Ajeet@gmail.com", "ajeetBank"));
		userDto = new UserDto();
		userDto.setName("manoj");
		userDto.setUsername("ManojKumar");
		userDto.setEmail("Manoj@gmail.com");
		objectMapper = new ObjectMapper();
		
		bookDto = new BookDto(1,"manoj", "ManojPublisher", "manojkumar");
		books = new ArrayList<>();
		books.add(new BookDto(1, "manoj", "ManojPublisher", "manojkumar"));
		books.add(new BookDto( 2,"ajeet", "AjeetPublisher", "ajeetBank"));
		bookDto = new BookDto();
		bookDto.setId(3);
		bookDto.setName("manoj");
		bookDto.setAuthor("ManojKumar");
		bookDto.setPublisher("ManojPublisher");
		
		libraryDtos=new ArrayList<>();
		libraryDto=new LibraryDto(1, "manoj", 1);
		libraryDtos.add(new Library(1, "manoj", 1));
	}

	@Test
	void addUserTest() throws Exception {
		
		ResponseEntity<UserDto> addUser=new ResponseEntity<>(userDto, HttpStatus.OK);
		String uri = "/library/users";
		when(feignService.addUser(userDto)).thenReturn(addUser);
		json = objectMapper.writeValueAsString(userDto);
		mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}
	
	
	@Test
	void viewUsersTest() throws Exception {
		ResponseEntity<List<UserDto>> response=new ResponseEntity<>(users, HttpStatus.OK);
		String uri = "/library/users";
		
		when(feignService.viewAllUsers()).thenReturn(response);
		json = objectMapper.writeValueAsString(users);
		mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}
	
	
	
	
	
	@Test
	void registrationWithInvalidTest() throws Exception {
		ResponseEntity<UserDto> addUser=new ResponseEntity<>(userDto, HttpStatus.OK);
		String uri = "/library/users";
		
		when(feignService.addUser(null)).thenReturn(addUser);
		json = objectMapper.writeValueAsString("Invalid  User Details");
		mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isBadRequest());
	}
	
	
	@Test
	void getUserByUsernameTest() throws Exception {
		ResponseEntity<UserDto> response=new ResponseEntity<>(userDto, HttpStatus.OK);
		String uri = "/library/users/manoj";
		
		
		when(feignService.getUserByUsername("manoj")).thenReturn(response);
		json = objectMapper.writeValueAsString(userDto);
		mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}
	
	
	
	@Test
	void updateUserByUserNameTest() throws Exception {
		ResponseEntity<UserDto> response=new ResponseEntity<>(userDto, HttpStatus.OK);
		String uri = "/library/users/2356563";
		
		userDto=new UserDto("ajeet", "Ajeet@gmail.com", "ajeetBank");
		when( feignService.updateUserByUserName("manoj", userDto)).thenReturn(response);
		json = objectMapper.writeValueAsString(userDto);
		mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}
	
	
	@Test
	void deleteUserTest() throws Exception {
		ResponseEntity<String> response=new ResponseEntity<>("User deleted Successfully", HttpStatus.OK);
		String uri = "/library/users/manoj";
		
		when(feignService.deleteUser("manoj")).thenReturn(response);
		json = objectMapper.writeValueAsString("User deleted Successfully");
		mockMvc.perform(delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}

	
	
	@Test
	void viewBooksTest() throws Exception {
		ResponseEntity<List<BookDto>> response=new ResponseEntity<>(books, HttpStatus.OK);
		String uri = "/library/books";
		
		when(feignService.viewAllBooks()).thenReturn(response);
		json = objectMapper.writeValueAsString(books);
		mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}
	
	
	
	
	@Test
	void addBookTest() throws Exception {
		ResponseEntity<BookDto> response=new ResponseEntity<>(bookDto, HttpStatus.OK);
		String uri = "/library/books";
		
		when(feignService.addBook(bookDto)).thenReturn(response);
		json = objectMapper.writeValueAsString(response);
		mockMvc.perform(post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
				.andExpect(status().isOk());
	}
	
	
	
	
	@Test
	void getBookDetailsByBookIdTest() throws Exception {
		ResponseEntity<Object> response=new ResponseEntity<>(bookDto, HttpStatus.OK);
		String uri = "/library/books/1";
		
		when(feignService.getBookDetailsByBookId(1)).thenReturn(response);
		json = objectMapper.writeValueAsString(books);
		mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}
	
	
	@Test
	void updateBookByIdTest() throws Exception {
		ResponseEntity<BookDto> response=new ResponseEntity<>(bookDto, HttpStatus.OK);
		String uri = "/library/books/1";
		
		bookDto=new BookDto(1,"ajeet", "Ajeetgmail", "ajeetBank");
		when( feignService.updateBookById(1,bookDto)).thenReturn(response);
		json = objectMapper.writeValueAsString(bookDto);
		mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}
	
	
	
	@Test
	void deleteBookByBookIdTest() throws Exception {
		ResponseEntity<String> response=new ResponseEntity<>("Book deleted Successfully", HttpStatus.OK);
		String uri = "/library/books/1";
		
		when(feignService.deleteBookById(1)).thenReturn(response);
		json = objectMapper.writeValueAsString("Book deleted Successfully");
		mockMvc.perform(delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}

	
	@Test
	void issueBookToUserTest() throws Exception {
		ResponseEntity<String> response=new ResponseEntity<>(libraryService.issueBookToUser("manoj",1), HttpStatus.OK);
		String uri = "/library/users/manoj/books/1";
		
		when(libraryService.issueBookToUser("manoj",1)).thenReturn("Book Issued Successfully");
		json = objectMapper.writeValueAsString(response);
		mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}

	@Test
	void returnBookTest() throws Exception {
		ResponseEntity<String> response=new ResponseEntity<>("book Returned successfully", HttpStatus.OK);
		String uri = "/library/users/manoj/books/1";
		
		when(libraryService.returnBook("manoj",1)).thenReturn(true);
		json = objectMapper.writeValueAsString(response);
		mockMvc.perform(delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}

	
	@Test
	void viewLibraryDetailsTest() throws Exception {
		ResponseEntity<List<Library>> response=new ResponseEntity<>(libraryDtos, HttpStatus.OK);
		
		String uri = "/library/manoj";
		
		when(libraryService.viewAllBooksByUserName("manoj")).thenReturn(libraryDtos);
		json = objectMapper.writeValueAsString(response);
		mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}
	
	
	
	
	
	@Test
	void viewLibraryInvalidDetailsTest() throws Exception {
		List<Library> viewAllBooksByUserName = libraryService.viewAllBooksByUserName("manoj");
		ResponseEntity<List<Library>> response=new ResponseEntity<>(viewAllBooksByUserName, HttpStatus.OK);
		
		String uri = "/library/manoj";
		
		when(libraryService.viewAllBooksByUserName("manoj")).thenReturn(viewAllBooksByUserName);
		json = objectMapper.writeValueAsString(response);
		mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isBadRequest());
	}

	
	
	
	
	
}
