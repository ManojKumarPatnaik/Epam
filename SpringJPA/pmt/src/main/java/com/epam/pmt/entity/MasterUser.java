package com.epam.pmt.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Master_User_Details")
@Data
@EqualsAndHashCode
public class MasterUser {
	


	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int userid;

	@OneToMany(mappedBy = "masterUser",fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	private List<Account> account;


	@Id
	@Column(name = "User_Name")
	private String username;
	@Column(name = "Password")
	private String password;


	public MasterUser() {
		super();
	}
	public MasterUser(int userid, String username, String password) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
	}
	@Override
	public String toString() {
		return "MasterUser [userid=" + userid +  ", username=" + username + ", password="
				+ password + "]";
	}

	public MasterUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}


}


