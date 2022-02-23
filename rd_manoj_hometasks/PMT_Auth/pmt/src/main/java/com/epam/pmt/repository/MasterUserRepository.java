package com.epam.pmt.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.epam.pmt.entity.MasterUser;

public interface MasterUserRepository  extends CrudRepository<MasterUser, Integer>{
	
	boolean existsByUsername(String username);
	
	Optional<MasterUser> findByUsernameAndPassword(String username,String password);
	
	MasterUser findByUsername(String userName);
}
