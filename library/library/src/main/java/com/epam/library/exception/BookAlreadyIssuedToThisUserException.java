package com.epam.library.exception;

public class BookAlreadyIssuedToThisUserException  extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookAlreadyIssuedToThisUserException(String message) {
		super(message);
	}
}