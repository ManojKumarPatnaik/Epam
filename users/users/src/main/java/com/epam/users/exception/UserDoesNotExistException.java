package com.epam.users.exception;

public class UserDoesNotExistException  extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserDoesNotExistException(String message) {
		super(message);
	}
}