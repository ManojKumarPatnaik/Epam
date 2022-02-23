package com.epam.library.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "master_user_details")
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class UserAuth {

	@Id
	@Column(name = "User_Name")
	private String username;

	public UserAuth(String username, String password) {
		super();
		this.username = username;
		this.password = password;

	}


	private String password;

}
