package com.epam.library.exception;

public class RecordNotFoundException  extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private static  final String MESSAGE = "Book with this id is not found issued to this username";

	public RecordNotFoundException() {
		super(MESSAGE);
	}
}