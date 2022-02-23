package com.epam.users;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.users.exception.NoRecordsException;
import com.epam.users.exception.UserAlreadyExistException;
import com.epam.users.exception.UserDoesNotExistException;
import com.epam.users.model.User;
import com.epam.users.repository.UserRepository;
import com.epam.users.service.UserServiceImpl;


@ExtendWith(MockitoExtension.class)

@ExtendWith(SpringExtension.class)
public class ServiceTest {


	List<User> users;


	@Mock
	User user;



	@MockBean
	UserRepository userRepository;

	@InjectMocks
	UserServiceImpl userService;

	@BeforeEach
	void setUp() {
		user = new User("manoj", "Manoj@gmail.com", "manojkumar");
		users = new ArrayList<>();
		users.add(new User( "manoj", "Manoj@gmail.com", "manojkumar"));
		users.add(new User( "ajeet", "Ajeet@gmail.com", "ajeetBank"));
	}

	@Test
	void addUserTest() {

		when(userRepository.save(user)).thenReturn(user);

		Assertions.assertEquals(user, userService.addUser(new User("manoj", "Manoj@gmail.com", "manojkumar")));

	}
	
	
	@Test
	void addUserWithInvalidDataTest() {
		when(userRepository.findByUsername("manoj")).thenReturn(user);
		Assertions.assertThrows(UserAlreadyExistException.class, ()->userService.addUser(user));
		
	}
	
	@Test
	void addUserWithNullDataTest() {

		when(userRepository.save(null)).thenReturn(null);

		Assertions.assertThrows(NullPointerException.class, ()->userService.addUser(null));

	}
	

	@Test
	void viewAllUsersTest() {

		when(userRepository.findAll()).thenReturn(users);

		Assertions.assertEquals(users, userService.viewAllUsers());

	}
	
	
	@Test
	void viewAllUsersWithInvalidTest() {
		List<User> users=new ArrayList<>();
		when(userRepository.findAll()).thenReturn(users);
		Assertions.assertThrows(NoRecordsException.class,()-> userService.viewAllUsers());

	}
	
	
	@Test
	void viewAllUsersWithNullTest() {
		when(userRepository.findAll()).thenReturn(null);
		Assertions.assertThrows(NullPointerException.class,()-> userService.viewAllUsers());

	}
	
	@Test
	void getUserByUsernameTest() {

		when(userRepository.findByUsername("manoj")).thenReturn(user);

		Assertions.assertEquals(user, userService.getUserByUsername("manoj"));

	}
	
	
	@Test
	void getUserByUsernameWithInvalidDataTest() {

		when(userRepository.findByUsername("78383")).thenReturn(null);

		Assertions.assertThrows(UserDoesNotExistException.class,()->userService.getUserByUsername("78383"));

	}
	
	
	
	
	
	
	@Test
	void updateUserByUserNameTest() {
		when(userRepository.findByUsername("manoj")).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);

		Assertions.assertEquals(user, userService.updateUserByUserName(user));

	}
	
	
	@Test
	void updateUserByUserNameWithInvalidDataTest() {

		when(userRepository.findByUsername("manojkumar")).thenReturn(user);
		Assertions.assertThrows(UserDoesNotExistException.class, ()->userService.updateUserByUserName(user));
		

	}
	
	
	@Test
	void deleteUserByUserNameTest() {

		when(userRepository.findByUsername("manoj")).thenReturn(user);
		doNothing().when(userRepository).deleteByUsername("manoj");
		Assertions.assertEquals(true, userService.deleteUserByUserName("manoj"));

	}
	
	@Test
	void deleteUserByUserNameWithInvalidTest() {

		when(userRepository.findByUsername("manojkumar")).thenReturn(null);
		Assertions.assertThrows(UserDoesNotExistException.class, ()->userService.deleteUserByUserName("manojkumar"));
		


	}
	
}
