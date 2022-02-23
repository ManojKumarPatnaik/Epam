package com.epam.library.dto;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryDto {
	@Id
	private int id;
	private String username;
	private int bookId;
	
	
	
	
}
