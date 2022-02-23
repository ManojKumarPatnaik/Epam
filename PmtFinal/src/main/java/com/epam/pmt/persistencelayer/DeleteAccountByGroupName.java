package com.epam.pmt.persistencelayer;

import java.util.List;

import com.epam.pmt.databaselayer.AccountDataBase;
import com.epam.pmt.entity.Account;
import com.epam.pmt.interfaces.DeleteAccountInterface;
import com.epam.pmt.persistencemanager.CrudOperations;

public class DeleteAccountByGroupName implements DeleteAccountInterface {


	
	public void deleteAccountByGroup(AccountDataBase data, String groupName) {
		
		@SuppressWarnings("unused")
		List<Account> accountDetailsList =CrudOperations.getAccountList();
		CrudOperations.deleteAccountByGroupName( groupName);
		
		
	}
}














//	 DeleteByGroupName deleteByGroupName=new DeleteByGroupName();
//		try {
//			accountDetailsList =CrudOperations.getAccountList();
////					data.getAccountList();
//			CrudOperations.deleteAccountByGroupName(accountDetailsList, groupName);
////			System.out.println(deleteByGroupName.deleteAccount(accountDetailsList, groupName));
//		} catch (NoRecordFoundForGroup e) {
//			e.printStackTrace();
//		}