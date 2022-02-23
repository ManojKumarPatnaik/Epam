package com.epam.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.users.exception.NoRecordsException;
import com.epam.users.exception.UserAlreadyExistException;
import com.epam.users.exception.UserDoesNotExistException;
import com.epam.users.model.User;
import com.epam.users.repository.UserRepository;
import com.epam.users.utils.Constants;

@Service
public class UserServiceImpl implements UserService{
		@Autowired
		UserRepository userRepository;
		
	
	
	@Override
	public User addUser(User user) throws UserAlreadyExistException{
		User userDetails =userRepository.findByUsername(user.getUsername());
		if(userDetails!=null) {
			  throw new UserAlreadyExistException(Constants.ALREADY);
		}
		
		return userRepository.save(user);
	}



	@Override
	public List<User> viewAllUsers()throws  NoRecordsException{
		List<User> users=userRepository.findAll();
		if(users.isEmpty()) {
			throw new NoRecordsException("No Record Found. Please Add a user");
		}
		return userRepository.findAll();
	}



	@Override
	public User getUserByUsername(String username)throws UserDoesNotExistException {
		User userDetails =userRepository.findByUsername(username);
		if(userDetails==null) {
			  throw new UserDoesNotExistException(Constants.USERNOTFOUND);
		}
		return userRepository.findByUsername(username);
	}



	@Override
	public User updateUserByUserName(User user) throws UserDoesNotExistException {
		User updateUser =userRepository.findByUsername(user.getUsername());
		if(updateUser==null) {
			  throw new UserDoesNotExistException(Constants.USERNOTFOUND);
		}
		return userRepository.save(user);
	}



	@Override
	public boolean deleteUserByUserName(String username)throws UserDoesNotExistException {
		User deleteUser=userRepository.findByUsername(username);
		boolean isDeleted=false;
			if(deleteUser == null) {
				throw new UserDoesNotExistException(Constants.USERNOTFOUND);
		}else {
			userRepository.deleteByUsername(username);
			isDeleted=true;
		}
		return isDeleted;
	}

}
