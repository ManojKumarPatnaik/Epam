package com.epam.books.exception;

public class NoRecordsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoRecordsException(String message) {
		super(message);
	}
}