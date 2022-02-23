package com.epam.users.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.epam.users.dto.ExceptionResponse;

@RestControllerAdvice
public class CustomExceptionHandeler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException message,
			WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		message.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String message1 = error.getDefaultMessage();
			errors.put(fieldName, message1);
		});
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), errors.toString(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserDoesNotExistException.class)
	public ResponseEntity<ExceptionResponse> handleAccountDoesNotExistException(UserDoesNotExistException message,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), message.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(NoRecordsException.class)
	public ResponseEntity<ExceptionResponse> handleNoRecordsExceptions(NoRecordsException message,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), message.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ExceptionResponse> handleUserAlreadyExistException(UserAlreadyExistException message,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), message.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
