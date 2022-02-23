package com.epam.books.exception;

public class BookAlreadyExitsException  extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookAlreadyExitsException(String message) {
		super(message);
	}
}