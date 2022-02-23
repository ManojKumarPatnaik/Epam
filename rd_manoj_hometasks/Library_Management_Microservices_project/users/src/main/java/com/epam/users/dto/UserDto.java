package com.epam.users.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.epam.users.utils.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	@NotBlank(message=Constants.USERNAME)
	@Pattern(regexp =Constants.USERNAME_REGEX ,message=Constants.USERNAME_ERROR)
	private String username;
	@NotBlank(message=Constants.EMAIL)
	@Email(message=Constants.EMAIL_ERROR)
	private String email;
	@NotBlank(message=Constants.NAME)
	@Pattern(regexp = Constants.NAME_REGEX,message=Constants.ERROR_NAME)
	private String name;
	

	
	
}
