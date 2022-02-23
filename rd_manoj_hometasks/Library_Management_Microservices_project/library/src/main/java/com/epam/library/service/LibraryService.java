package com.epam.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.library.exception.BookAlreadyIssuedToThisUserException;
import com.epam.library.exception.BooksLimitExceededException;
import com.epam.library.exception.RecordNotFoundException;
import com.epam.library.feign.Bookfeign;
import com.epam.library.feign.UserFeign;
import com.epam.library.model.Library;
import com.epam.library.repository.LibraryRepository;

@Service
public class LibraryService {
	
	@Autowired
	UserFeign userFeign;
	@Autowired
	Bookfeign bookfeign;
	@Autowired
	LibraryRepository libraryRepository;
	@Autowired
	FeignService feignService;

	public boolean returnBook(String username, int bookId) throws RecordNotFoundException {
		boolean isDeleted = false;
		Optional<Library> findUser = libraryRepository.findByUsernameAndBookId(username, bookId);
		if (findUser.isEmpty()) {
			throw new RecordNotFoundException();
		} else {
			libraryRepository.deleteByUsernameAndBookId(username, bookId);
			isDeleted = true;

		}
		return isDeleted;
	}
	
	public List<Library> findByUsername(String username){
		return libraryRepository.findByUsername(username);
	}
	
	
	public String issueBookToUser(String username, int bookId) throws BookAlreadyIssuedToThisUserException,BooksLimitExceededException {
		userFeign.getUserByUsername(username);
		bookfeign.getBookDetailsByBookId(bookId);
		if(libraryRepository.findByUsernameAndBookId(username, bookId).isPresent()) {
			throw new BookAlreadyIssuedToThisUserException("Book is Already Issued");
		}
		int numberOfBooksPerUser=findByUsername(username).size();
		if(numberOfBooksPerUser<3) {
			libraryRepository.save(new Library(username,bookId));
		}
		else throw new BooksLimitExceededException("User already borrowed 3 books, return a book before borrowing new book");
		return "Book Issued Successfully to the "+username;
	}
	
	
	public List<Library> viewAllBooksByUserName(String username) throws RecordNotFoundException{
		List<Library> findByUsername = libraryRepository.findByUsername(username);
		if(findByUsername.isEmpty()) {
			throw new RecordNotFoundException();
		}
		return findByUsername;
	}
	
	
	
	
	

}
