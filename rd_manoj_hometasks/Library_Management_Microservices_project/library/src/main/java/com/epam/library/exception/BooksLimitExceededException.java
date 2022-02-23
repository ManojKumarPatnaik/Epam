package com.epam.library.exception;

public class BooksLimitExceededException  extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BooksLimitExceededException(String message) {
		super(message);
	}
}