package com.epam.books.exception;

public class BookDoesNotExistException  extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookDoesNotExistException(String message) {
		super(message);
	}
}