package com.epam.users.exception;

public class NoRecordsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoRecordsException(String message) {
		super(message);
	}
}