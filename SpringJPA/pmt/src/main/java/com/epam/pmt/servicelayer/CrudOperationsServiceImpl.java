package com.epam.pmt.servicelayer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.pmt.database.CrudOperationsDbImpl;
import com.epam.pmt.encryptdecryptpassword.EncryptDecryptPassword;
import com.epam.pmt.encryptdecryptpassword.EncryptionAndDecryption;
import com.epam.pmt.entity.Account;
import com.epam.pmt.entity.MasterUser;
import com.epam.pmt.exceptions.AccountDoesNotExistException;
import com.epam.pmt.exceptions.InvalidUrlException;
import com.epam.pmt.exceptions.LoginFirstException;
import com.epam.pmt.exceptions.NoGroupFoundForAccount;
import com.epam.pmt.exceptions.NoRecordFoundForGroup;
import com.epam.pmt.presentationlayer.CurrentAccount;
import com.epam.pmt.utils.ConstantsUtils;
import com.epam.pmt.validation.AccountValidation;

@Service
public class CrudOperationsServiceImpl {
	@Autowired
	CrudOperationsDbImpl crudOperationsDao;
	@Autowired
	EncryptDecryptPassword encryptDecryptPassword;
	@Autowired
	EncryptionAndDecryption encrypt;
	@Autowired
	AccountValidation accountValidation;

	
	public List<Account> getAccountByGroupName(String groupName, MasterUser currentMasterUser) throws NoGroupFoundForAccount {
		List<Account> accountDetailsList = (crudOperationsDao.listAccountByGroupName(groupName,currentMasterUser));
		if (accountDetailsList.isEmpty()) {
			throw new NoGroupFoundForAccount("No group present with that name");
		}
		return accountDetailsList;
	}


	public boolean deleteAccountByGroup(String groupName, MasterUser currentMasterUser) throws NoRecordFoundForGroup {
		boolean isDeletedGroup = false;
		List<Account> accountDetailsList = crudOperationsDao.getAccountList(currentMasterUser).stream()
				.filter(account -> account.getGroup().equals(groupName)).collect(Collectors.toList());

		isDeletedGroup = crudOperationsDao.deleteAccountByGroupName(groupName,currentMasterUser);
		if (accountDetailsList.isEmpty()) {
			throw new NoRecordFoundForGroup("Oops! No Record found for group");
		}

		return isDeletedGroup;
	}

	public boolean deleteAccountByUrl(String url, MasterUser currentMasterUser) throws InvalidUrlException {
		boolean isDeleted = false;
		List<Account> accountDetailsList = crudOperationsDao.getAccountList(currentMasterUser).stream()
				.filter(account -> account.getUrl().equals(url)).collect(Collectors.toList());

		if (!accountDetailsList.isEmpty()) {
			isDeleted = (crudOperationsDao.deleteAccountByUrl(url,currentMasterUser));
		} else {
			throw new InvalidUrlException(ConstantsUtils.URLMESSAGE);
		}

		return isDeleted;
	}

	public String getPassword( String accountUsername, MasterUser currentMasterUser) {
		String decryptPassword = null;
		try {
			decryptPassword = encrypt.getDecryptedPassword(crudOperationsDao.getDecryptedPasswordList(accountUsername,currentMasterUser),  accountUsername);
			if (decryptPassword.isEmpty()) {
				throw new AccountDoesNotExistException("Oops! No account is present");
			}
			
		} catch (AccountDoesNotExistException message) {
			ConstantsUtils.logger.info(message);
		}
		return decryptPassword;
	}

	public boolean addAccount(Account account, MasterUser currentMasterUser) {
		boolean isAdd = false;
//		MasterUser current = CurrentAccount.getInstance().getCurrentUser();
		
		account.setMasterUser(currentMasterUser);
		if (currentMasterUser == null) {
			throw new LoginFirstException("Please login first!");

		}
		isAdd = crudOperationsDao.add(account);
		return isAdd;
	}

	public List<Account> viewAllAccounts(MasterUser currentMasterUser) {
		List<Account> accountDetailsList = null;
		try {
			accountDetailsList = crudOperationsDao.getAccountList(currentMasterUser);

			if (accountDetailsList.isEmpty()) {
				throw new AccountDoesNotExistException("No Accounts present with that user name");
			}
		} catch (AccountDoesNotExistException message) {
			ConstantsUtils.logger.info(message);
		}
		return accountDetailsList;
	}

	public List<MasterUser> viewAllUsers() throws AccountDoesNotExistException {

		List<MasterUser> usersDetailsList = crudOperationsDao.getAllUsers();

		if (usersDetailsList.isEmpty()) {
			throw new AccountDoesNotExistException("No Users present in database. ");
		}

		return usersDetailsList;
	}

	
	public Account getAccountById(int id) {

		return crudOperationsDao.getAccountById(id);
	}

	public void updateAccount(Account account, MasterUser currentMasterUser) {
		crudOperationsDao.updateAccountDetails(account.getAccountId(), account.getUrl(), account.getUsername(),
				account.getPassword(), account.getGroup());
	}

}
