package com.epam.users;

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

import com.epam.users.dto.UserDto;
import com.epam.users.model.User;
import com.epam.users.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class UsersApplicationTests {
	@Autowired
	MockMvc mockMvc;
	String json;
	ObjectMapper objectMapper;
	@MockBean
	UserService userService;
	User user;
	UserDto userDto;
	List<User> users;
	
	@BeforeEach
	void setUp() {
		user = new User("manoj", "Manoj@gmail.com", "manojkumar");
		users = new ArrayList<>();
		users.add(new User( "manoj", "Manoj@gmail.com", "manojkumar"));
		users.add(new User( "ajeet", "Ajeet@gmail.com", "ajeetBank"));
		userDto = new UserDto();
		userDto.setName("manoj");
		userDto.setUsername("ManojKumar");
		userDto.setEmail("Manoj@gmail.com");
		objectMapper = new ObjectMapper();

	}

	

	@Test
	void viewUsersTest() throws Exception {
		String uri = "/users";
		when(userService.viewAllUsers()).thenReturn(users);
		json = objectMapper.writeValueAsString(users);
		mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}
	
	
	
	@Test
	void registrationTest() throws Exception {
		String uri = "/users";
		when(userService.addUser(new User("manoj", "Manoj@gmail.com", "manojkumar"))).thenReturn(user);
		json = objectMapper.writeValueAsString(user);
		mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}
	
	@Test
	void registrationWithInvalidTest() throws Exception {
		String uri = "/users";
		when(userService.addUser(null)).thenReturn(null);
		json = objectMapper.writeValueAsString(user);
		mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isBadRequest());
	}
	@Test
	void viewUsersInvalidTest() throws Exception {
		String uri = "/users";
		when(userService.viewAllUsers()).thenReturn(null);
		json = objectMapper.writeValueAsString(users);
		mockMvc.perform(get(uri)
		.contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void getUserByUsernameTest() throws Exception {
		String uri = "/users/manoj";
		when(userService.getUserByUsername("manoj")).thenReturn(user);
		json = objectMapper.writeValueAsString(user);
		mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}
	
//	@Test
//	void getUserByUsernameWithInvalidTest() throws Exception {
//		String uri = "/users/2356563";
//		when(userService.getUserByUsername("36673")).thenReturn(null);
//		json = objectMapper.writeValueAsString(null);
//		mockMvc.perform(get(uri)
//				.contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
//				.andExpect(status().isBadRequest());
//	}
	
	@Test
	void updateUserByUserNameTest() throws Exception {
		String uri = "/users/manoj";
		when(userService.getUserByUsername("manoj")).thenReturn(user);
		user=new User("ajeet", "Ajeet@gmail.com", "ajeetBank");
		when( userService.updateUserByUserName(user)).thenReturn(user);
		json = objectMapper.writeValueAsString(user);
		mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}
	@Test
	void updateUserByUserNameWithInvalidTest() throws Exception {
		String uri = "/users/34642";
		when(userService.getUserByUsername("364673")).thenReturn(null);
		user=new User("ajeet", "Ajeet@gmail.com", "ajeetBank");
		when( userService.updateUserByUserName(user)).thenReturn(null);
		json = objectMapper.writeValueAsString(user);
		mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void deleteUserTest() throws Exception {
		String uri = "/users/manoj";
		when(userService.deleteUserByUserName("manoj")).thenReturn(true);
		json = objectMapper.writeValueAsString("User deleted Successfully");
		mockMvc.perform(delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isOk());
	}

	
	@Test
	void deleteUserInvalidTest() throws Exception {
		String uri = "/users/6556";
		when(userService.deleteUserByUserName("656")).thenReturn(false);
		json = objectMapper.writeValueAsString("Unable to find the User name.");
		mockMvc.perform(delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andExpect(status().isNotFound());
	}
}
