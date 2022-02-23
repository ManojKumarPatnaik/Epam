package com.epam.library.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.library.model.UserAuth;
@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserAuth, String>{


	


}