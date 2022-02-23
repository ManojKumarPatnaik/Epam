package com.epam.users.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

import com.epam.users.dto.UserDto;
import com.epam.users.model.User;
import com.epam.users.service.UserService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	Environment environment;
	

	@PostMapping("")
	public ResponseEntity<User> addUser(@RequestBody @Valid UserDto userDto) {
		

		User user = userService.addUser(new User(userDto.getUsername(), userDto.getEmail(), userDto.getName()));
		return new ResponseEntity<>(user,user != null ?  HttpStatus.OK:HttpStatus.BAD_REQUEST);
	}

	@GetMapping("")
	public ResponseEntity<List<User>> viewAllUsers() {
		List<User> viewAllUsers = userService.viewAllUsers();
		return new ResponseEntity<>(viewAllUsers,viewAllUsers!=null? HttpStatus.OK:HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/{userName}")
	public ResponseEntity<User> getUserByUsername(@PathVariable("userName") String username) {
		User userDetails = userService.getUserByUsername(username);
		log.info(environment.getProperty("local.server.port"));
		return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}

	@PutMapping("/{userName}")
	public ResponseEntity<User> updateUserByUserName(@PathVariable("userName") String username,
			@RequestBody @Valid UserDto userDto) {

		User user = userService.getUserByUsername(username);
		user = new User(userDto.getUsername(), userDto.getEmail(), userDto.getName());
		user = userService.updateUserByUserName(user);
		return new ResponseEntity<>(user,user!=null? HttpStatus.OK:HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/{userName}")
	public ResponseEntity<String> deleteUser(@PathVariable("userName") String username) {
		boolean isDeleted = false;
		if (userService.deleteUserByUserName(username)) {
			isDeleted = true;

		}
		return new ResponseEntity<>(isDeleted ? "User deleted Successfully" : "Unable to find the User name.",
				isDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

}
