package com.epam.pmt.servicelayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.epam.pmt.exceptions.PasswordMismatchException;
import com.epam.pmt.presentationlayer.CurrentAccount;
import com.epam.pmt.validation.AccountValidation;

@Service
public class CrudServiceImpl implements CrudService {
	@Autowired
	CrudOperationsDbImpl crud;
	@Autowired
	EncryptDecryptPassword encryptDecryptPassword;
	@Autowired
	EncryptionAndDecryption encrypt;
	@Autowired
	AccountValidation accountValidation;
	private static final Logger logger = LogManager.getLogger(CrudServiceImpl.class);
	private static final  String MESSAGE="Oops! Entered URL is Invalid";
	@Override
	public List<Account> getAccountByGroupName(String groupName) {
		List<Account> listOfGroupName;

			List<Account> accountDetailsList = crud.getAccountList().stream()
					.filter(account -> account.getGroup().equals(groupName)).collect(Collectors.toList());

			if (!accountDetailsList.isEmpty()) {
				listOfGroupName = (crud.listAccountByGroupName(groupName));
				logger.info(listOfGroupName);
			} else
				throw new NoGroupFoundForAccount("No group present with that name");

		return listOfGroupName;
	}

	@Override
	public List<Account> modifyGroupDetails(String groupName, String newGroup) {
		List<Account> result ;
			List<Account> accountDetailsList = crud.getAccountList().stream()
					.filter(account -> account.getGroup().equals(groupName)).collect(Collectors.toList());

			if (!accountDetailsList.isEmpty()) {
				result = (crud.updateAccountByGroupName(groupName, newGroup));
				logger.info(result);
			} else {
				throw new NoGroupFoundForAccount("No group present with that name");
			}

		return result;
	}

	@Override
	public List<Account> modifyPassword(String url, String newPassword) {
		List<Account> result = new ArrayList<>();
		try {
			List<Account> accountDetailsList = crud.getAccountList().stream()
					.filter(account -> account.getUrl().equals(url)).collect(Collectors.toList());
			if (!accountDetailsList.isEmpty()) {
				String encrypted = encryptDecryptPassword.encrypt(newPassword);
				result = (crud.updateAccountPassword(url, encrypted));
				logger.info(result);
			} else {
				throw new InvalidUrlException(MESSAGE);
			}

		} catch (PasswordMismatchException | InvalidUrlException message) {
			logger.info("No records present with that data");

		} catch (Exception e) {
			logger.info("Other exception : {}" , e.getMessage());

		}
		return result;
	}

	@Override
	public boolean deleteAccountByGroup(String groupName) {
		boolean isDeletedGroup = false;
			List<Account> accountDetailsList = crud.getAccountList().stream()
					.filter(account -> account.getGroup().equals(groupName)).collect(Collectors.toList());

			if (!accountDetailsList.isEmpty()) {
				isDeletedGroup= crud.deleteAccountByGroupName(groupName);
				
			} else {
				throw new NoRecordFoundForGroup("Oops! No Record found for group");
			}

		return isDeletedGroup;
	}

	@Override
	public boolean modifyUrl(String url, String newUrl) {
		boolean result = false;
			List<Account> accountDetailsList = crud.getAccountList().stream()
					.filter(account -> account.getUrl().equals(url)).collect(Collectors.toList());

			if (!accountDetailsList.isEmpty()) {
				crud.updateAccountUrl(url, newUrl);
				result = true;
				logger.info(result);
			} else {
				throw new InvalidUrlException(MESSAGE);
			}

		return result;
	}

	@Override
	public boolean deleteAccountByUrl(String url) {
		boolean isDeleted = false;
			List<Account> accountDetailsList = crud.getAccountList().stream()
					.filter(account -> account.getUrl().equals(url)).collect(Collectors.toList());

			if (!accountDetailsList.isEmpty()) {
				isDeleted = (crud.deleteAccountByUrl(url));
			} else {
				throw new InvalidUrlException(MESSAGE);
			}

		return isDeleted;
	}

	@Override
	public String getPassword( String url, String accountUsername) {
		String decryptPassword = "";
		String temporary = null;
		try {
			List<Account> accountList = crud.getDecryptedPassword(url, accountUsername);
			if (!accountList.isEmpty()) {
				
				temporary = encrypt.getEncryptedPassword(accountList, url, accountUsername);
				if (!temporary.equals("")&&accountValidation.isValidURL(url)) {
					
					logger.info("The Encrypted password is : {}" , temporary);


					decryptPassword = encrypt.getDecryptedPassword(accountList, url, accountUsername);

					logger.info("The Decrypted password is : {}" , decryptPassword);
				} else {
					throw new InvalidUrlException(MESSAGE);
				}
			} else {
				throw new AccountDoesNotExistException("Oops! No account is present");
			}

		} catch (AccountDoesNotExistException | InvalidUrlException message) {
			logger.info("Account doesn't exits in database.");
		}

		return decryptPassword;
	}

	@Override
	public void addAccount(Account account) {
		MasterUser current=CurrentAccount.getInstance().getCurrentUser();
		account.setMasterUser(current);
		if(current==null) {
			throw new LoginFirstException("Please login first!");
		}
		crud.add(account);
		
	}

	@Override
	public List<Account> viewAllAccounts() {
		List<Account> accountDetailsList = null;
		try {
			accountDetailsList = crud.getAccountList();

			if (!accountDetailsList.isEmpty()) {
				logger.info(accountDetailsList);
			} else
				throw new AccountDoesNotExistException("No Accounts present with that user name");
		}catch(AccountDoesNotExistException message) {
			logger.info(message);
		}
		return accountDetailsList;
	}
	@Override
	public List<MasterUser> viewAllUsers() {

			List<MasterUser> usersDetailsList = crud.getAllUsers();

			if (!usersDetailsList.isEmpty()) {
				logger.info(usersDetailsList);
			} else
				throw new AccountDoesNotExistException("No Users present in database. ");

		return usersDetailsList;
	}

	public Account getAccountById(int id) {

			return crud.getAccountById(id);
	}
	public void updateAccount(Account account) {
			crud.updateAccountDetails(account.getAccountId(),account.getUrl(),account.getUsername(),account.getPassword(),account.getGroup());
	}
	

}
