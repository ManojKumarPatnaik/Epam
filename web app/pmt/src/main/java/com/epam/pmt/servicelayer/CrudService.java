package com.epam.pmt.servicelayer;

import java.util.List;

import com.epam.pmt.entity.Account;
import com.epam.pmt.entity.MasterUser;

public interface CrudService {
	void addAccount(Account account);
	
	List<Account> getAccountByGroupName(String  groupName);

	List<Account> modifyGroupDetails(String groupName, String newGroup);

	List<Account> modifyPassword(String url, String newPassword);

	boolean deleteAccountByGroup(String groupName);

	boolean modifyUrl(String url, String newUrl);

	boolean deleteAccountByUrl(String url);

	String getPassword( String url, String accountUsername);
	
	List<Account> viewAllAccounts();
	
	List<MasterUser> viewAllUsers();

}
