package com.epam.library.service;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.epam.library.model.UserAuth;
import com.epam.library.repository.UserRepository;



@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserAuth> findByUsername=userRepository.findById(username);
		if(findByUsername.isEmpty()) {
			throw new UsernameNotFoundException("No Records present with that name.");
		}
		String password=new BCryptPasswordEncoder().encode(findByUsername.get().getPassword());
		return new User(findByUsername.get().getUsername(),password,new ArrayList<>());
	}
}