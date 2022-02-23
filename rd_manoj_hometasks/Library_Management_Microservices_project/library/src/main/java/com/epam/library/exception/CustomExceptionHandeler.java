package com.epam.library.exception;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.epam.library.dto.ExceptionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;


@RestControllerAdvice
public class CustomExceptionHandeler {
	
@Autowired
Environment environment;
	
	
	
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ExceptionResponse> handleFeignException(FeignException message,
			WebRequest request) throws  JsonProcessingException {
		ObjectMapper objectMapper=new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, String> errors = objectMapper.readValue(message.contentUTF8(), Map.class);
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), errors.get("message"),request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.valueOf(message.status()));
	}
	
	@ExceptionHandler(UserDoesNotExistException.class)
	public ResponseEntity<ExceptionResponse> handleAccountDoesNotExistException(UserDoesNotExistException message,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), message.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	

	@ExceptionHandler(BookAlreadyIssuedToThisUserException.class)
	public ResponseEntity<ExceptionResponse> handleBookAlreadyIssuedToThisUserException(BookAlreadyIssuedToThisUserException message,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), message.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleRecordNotFoundException(RecordNotFoundException message,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), message.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BooksLimitExceededException.class)
	public ResponseEntity<ExceptionResponse> handleBooksLimitExceededException(BooksLimitExceededException message,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), message.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
