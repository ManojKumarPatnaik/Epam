package com.epam.library.feign;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.library.dto.UserDto;

@FeignClient(name="User-Service",fallback = UserfeignImpl.class)
@LoadBalancerClient(name="User-Service",configuration =  UserfeignImpl.class)
public interface UserFeign {

	@PostMapping("/users")
	public ResponseEntity<UserDto> addUser(@RequestBody  UserDto userDto);
	
	@GetMapping("/users")
	public  ResponseEntity<List<UserDto>> viewAllUsers(); 
	
	
	@GetMapping("/users/{userName}")
	public  ResponseEntity<UserDto> getUserByUsername( @PathVariable("userName")String username ); 
	
	@PutMapping("/users/{userName}")
	public ResponseEntity<UserDto> updateUserByUserName(@PathVariable("userName") String username,
			@RequestBody  UserDto userDto);
	
	
	@DeleteMapping("/users/{userName}")
	public  ResponseEntity<String>  deleteUser(@PathVariable("userName")  String username); 
	
	
}
