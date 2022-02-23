package com.epam.library.dto;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.epam.library.utils.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
	
	
	@Id
	private int id;
	@NotBlank(message=Constants.NAME)
	@Pattern(regexp =Constants.NAME_REGEX ,message=Constants.ERROR_NAME)
	private String name;
	@NotBlank(message=Constants.PUBLISHER)
	@Pattern(regexp = Constants.PUBLISHER_REGEX,message=Constants.ERROR_PUBLISHER)
	private String publisher;
	@NotBlank(message=Constants.AUTHOR)
	@Pattern(regexp = Constants.AUTHOR_REGEX,message=Constants.ERROR_AUTHOR)
	private String  author;

}
