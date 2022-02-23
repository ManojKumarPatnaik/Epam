package com.epam.pmt.persistencelayer;

import java.util.List;
import java.util.Scanner;

import com.epam.pmt.databaselayer.AccountDataBase;
import com.epam.pmt.entity.Account;
import com.epam.pmt.interfaces.ListPasswordAccountInterface;
import com.epam.pmt.persistencemanager.CrudOperations;

public class ListPasswordAccountAndGroup implements ListPasswordAccountInterface {

	@Override
	public void getAccountByGroupName(AccountDataBase data, String groupName) {
		
		
		List<Account> accountDetailsList = null;
		@SuppressWarnings({ "resource", "unused" })
		Scanner scanner = new Scanner(System.in);
		accountDetailsList = CrudOperations.getAccountList();
		System.out.println(CrudOperations.listAccountByGroupName(accountDetailsList, groupName));
		if (accountDetailsList.size() > 0) {
		}
		else
			System.out.println("No group present with that name");
		
		
	}

}









//				data.getAccountList();
//		DisplayAccountDetailsByGroup displayAccountDetailsByGroup = new DisplayAccountDetailsByGroup();
//		accountDetailsList = null;
//		try {
//			accountDetailsList = displayAccountDetailsByGroup.getListByGroup(accountDetailsList, groupName);
//		} catch (NoGroupFoundForAccount e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//			System.out.println(accountDetailsList);
//			for (Account account : accountDetailsList) {
//				System.out.println("Encrypted Password = " + account.getPassword() + ", URL = " + account.getUrl()
//						+ ", Group Name = " + account.getGroup() + ", Account Name = " + account.getUsername());
//			}