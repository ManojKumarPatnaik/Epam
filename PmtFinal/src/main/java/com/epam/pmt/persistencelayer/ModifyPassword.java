package com.epam.pmt.persistencelayer;

import java.util.List;
import java.util.Scanner;

import com.epam.pmt.databaselayer.AccountDataBase;
import com.epam.pmt.encryptdecryptpassword.EncryptDecryptPassword;
import com.epam.pmt.entity.Account;
import com.epam.pmt.interfaces.ModifyPasswordInterface;
import com.epam.pmt.persistencemanager.CrudOperations;

public class ModifyPassword implements ModifyPasswordInterface {

	@Override
	public List<Account> modifyPassword(AccountDataBase data, String url, String newPassword) {
		List<Account> accountDetailsList = null;
		@SuppressWarnings({ "resource", "unused" })
		Scanner scanner = new Scanner(System.in);
		accountDetailsList = CrudOperations.getAccountList();
		EncryptDecryptPassword obj = new EncryptDecryptPassword();
		String encrypted=obj.Encrypt(newPassword);
		CrudOperations.updateAccountPassword( url, encrypted);

		return accountDetailsList;
	}

}





















//		ModifyPasswordAccount modifyPasswordAccount = new ModifyPasswordAccount();
//		System.out.println(modifyPasswordAccount.modifyPassword(accountList, url, newPassword));