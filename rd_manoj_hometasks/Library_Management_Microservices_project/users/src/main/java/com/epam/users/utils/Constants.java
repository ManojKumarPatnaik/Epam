package com.epam.users.utils;

public class Constants {
	private Constants() {
		
	}
	public static final String USERNAME="UserName can't be blank";
	public static final String USERNAME_REGEX="^[a-zA-Z][a-zA-Z_$]{3,}$";
	public static final String USERNAME_ERROR="Invalid UserName";
	
	public static final String EMAIL="Email can't be blank";
	public static final String EMAIL_ERROR="Invalid Email";
	public static final String NAME="Name can't be blank";
	
	public static final String ERROR_NAME="Invalid name";
	public static final String ALREADY="UserName Already exits";
	public static final String NAME_REGEX="^[A-Za-z]{2,}";
	public static final String USERNOTFOUND="No Records present with that user name";
	
}
