package com.epam.users.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.epam.users.model.User;
@Transactional
public interface UserRepository extends JpaRepository<User, String>{

	User findByUsername(String username);
	@Modifying
	void deleteByUsername(String username);

	
	
	
}
