package com.epam.pmt.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class CurrentSession implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private int accountId;
	

}
