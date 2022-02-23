package com.epam.library.feign;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.epam.library.dto.UserDto;
import com.epam.library.exception.UserDoesNotExistException;
@Service
public class UserfeignImpl implements UserFeign {

	
	@Override
	public ResponseEntity<UserDto> addUser(UserDto userDto) {
		userDto.setEmail("AddUserfallback@gmail.com");
		
		userDto.setName("fallback-Adduser");
		userDto.setUsername("fallback_username");
		return new  ResponseEntity<>(userDto,HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<List<UserDto>> viewAllUsers() {
		UserDto userDto=new UserDto();
		userDto.setEmail("fallbackEmail");
		
		userDto.setName(null);
		userDto.setUsername("fallback");
		List<UserDto> listOfUsers=new ArrayList<>();
		listOfUsers.add(userDto);
		return new  ResponseEntity<>(listOfUsers,HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<UserDto> getUserByUsername(String username) {
	
		if(username!=null) {
			throw new UserDoesNotExistException("user doesn't exits");
		}
		UserDto userDto=new UserDto();
		userDto.setEmail("fallback@gmail.com");
		
		userDto.setName("getUserByUsername");
		userDto.setUsername(null);
		
		return new  ResponseEntity<>(userDto,HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<UserDto> updateUserByUserName(String username, UserDto userDto) {
		userDto.setEmail("Updatefallback@gmail.com");
		
		userDto.setName(null);
		userDto.setUsername("fallback username");
		return new  ResponseEntity<>(userDto,HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<String> deleteUser(String username) {
		return new ResponseEntity<> ("from fallback",HttpStatus.BAD_REQUEST);
	}

	

}
