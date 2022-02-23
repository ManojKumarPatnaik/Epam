package com.epam.pmt.persistencelayer;

import java.util.List;

import com.epam.pmt.databaselayer.AccountDataBase;
import com.epam.pmt.entity.Account;
import com.epam.pmt.interfaces.ModifyUrlInterface;
import com.epam.pmt.persistencemanager.CrudOperations;

public class ModifyUrl implements ModifyUrlInterface {

	@Override
	public List<Account> modifyUrl(AccountDataBase data, String url, String newUrl) {
		List<Account> accountDetailsList = null;
		accountDetailsList= CrudOperations.getAccountList();
		CrudOperations.updateAccountUrl( url, newUrl);
		return accountDetailsList;
	}

}
















//		@SuppressWarnings({ "resource", "unused" })
//		Scanner scanner = new Scanner(System.in);
//		ArrayList<Account> accountList = data.getAccountList();
//		ModifyPasswordAccount modifyPasswordAccount = new ModifyPasswordAccount();
//		try {
//			System.out.println(modifyPasswordAccount.modifyUrl(accountList, url, newUrl));
//		} catch (InvalidUrlException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}