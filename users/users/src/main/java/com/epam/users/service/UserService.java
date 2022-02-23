package com.epam.users.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.users.model.User;

@Service
public interface UserService {
	
	User addUser(User user);

	List<User> viewAllUsers();

	User  getUserByUsername(String username);

	User updateUserByUserName(User user);

	boolean deleteUserByUserName(String username);

}
