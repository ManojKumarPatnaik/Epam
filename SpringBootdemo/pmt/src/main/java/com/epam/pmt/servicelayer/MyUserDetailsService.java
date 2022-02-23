package com.epam.pmt.servicelayer;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.epam.pmt.entity.MasterUser;
import com.epam.pmt.repository.MasterUserRepository;


@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	MasterUserRepository masterUserRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MasterUser findByUsername = masterUserRepository.findByUsername(username);
		String password=new BCryptPasswordEncoder().encode(findByUsername.getPassword());
		
		return new User(findByUsername.getUsername(),password,new ArrayList<>());
	}

}
