package com.epam.pmt.testcases;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.epam.pmt.databaselayer.AccountDataBase;
import com.epam.pmt.entity.Account;
import com.epam.pmt.persistencelayer.CreateAccountDetails;

public class TestCreate {
	ArrayList<Account> account1=new ArrayList<>();
	AccountDataBase accountDataBase;
	CreateAccountDetails createAccountService;
	@BeforeEach
	void testC() {
	account1.add(new Account("https://www.sbi.com", "manoj", "Manoj1", "bank"));
	createAccountService=mock(CreateAccountDetails.class);
//			new CreateAccountDetails();
	accountDataBase=mock(AccountDataBase.class);
//			new AccountDataBase();
	accountDataBase.setAccountList(account1);
	}

	@Test
	public void testCreate() {
		Assertions.assertAll();
	}

}



	

