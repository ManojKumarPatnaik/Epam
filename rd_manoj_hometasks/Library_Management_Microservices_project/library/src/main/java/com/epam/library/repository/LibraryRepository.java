package com.epam.library.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.library.model.Library;
@Repository
@Transactional
public interface LibraryRepository extends JpaRepository<Library, Integer>{



	List<Library> findByUsername(String username);
	
		
		Optional<Library> findByUsernameAndBookId(String username, int bookId);
	


	void deleteByUsernameAndBookId(String username, int bookId);


}