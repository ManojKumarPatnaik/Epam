package com.epam.pmt.persistencelayer;

import java.util.List;
import java.util.Scanner;

import com.epam.pmt.databaselayer.AccountDataBase;
import com.epam.pmt.entity.Account;
import com.epam.pmt.interfaces.ModifyGroupDetailsInterface;
import com.epam.pmt.persistencemanager.CrudOperations;

public class ModifyGroupDetails implements ModifyGroupDetailsInterface {

	@Override
	public List<Account> modifyGroupDetails(AccountDataBase data, String groupName, String newGroup) {
		List<Account> accountDetailsList = null;
		@SuppressWarnings({ "resource", "unused" })
		Scanner scanner = new Scanner(System.in);
		accountDetailsList = CrudOperations.getAccountList();
		CrudOperations.updateAccountByGroupName( groupName, newGroup);
		return accountDetailsList;
	}

}




















//				data.getAccountList();
//		GroupModification modifyGroup = new GroupModification();
//		try {
//			System.out.println(modifyGroup.modifyGroupName(accountDetailsList, groupName, newGroup));
//		} catch (NoGroupFoundForAccount e) {
//			e.printStackTrace();
//		}