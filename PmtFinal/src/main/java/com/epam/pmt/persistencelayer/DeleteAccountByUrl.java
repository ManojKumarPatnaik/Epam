package com.epam.pmt.persistencelayer;

import java.util.List;

import com.epam.pmt.databaselayer.AccountDataBase;
import com.epam.pmt.entity.Account;
import com.epam.pmt.interfaces.DeleteAccountByUrlInterface;
import com.epam.pmt.persistencemanager.CrudOperations;

public class DeleteAccountByUrl implements DeleteAccountByUrlInterface {

	@Override
	public void deleteAccountByUrl(AccountDataBase data, String url) {
		
		
		
		@SuppressWarnings("unused")
		List<Account> accountDetailsList =CrudOperations.getAccountList();
		CrudOperations.deleteAccountByUrl( url);
		
		
	}
}













//		DeletePasswordAccount deletePasswordAccount = new DeletePasswordAccount();
//		try {
//			accountDetailsList = data.getAccountList();
//			System.out.println(deletePasswordAccount.deleteAccountPassword(accountDetailsList, url));
//
//		} catch (InvalidUrlException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}