package com.epam.pmt.servicelayer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.pmt.encryptdecryptpassword.EncryptDecryptPassword;
import com.epam.pmt.encryptdecryptpassword.EncryptionAndDecryption;
import com.epam.pmt.entity.Account;
import com.epam.pmt.entity.MasterUser;
import com.epam.pmt.exceptions.AccountDoesNotExistException;
import com.epam.pmt.exceptions.InvalidUrlException;
import com.epam.pmt.exceptions.LoginFirstException;
import com.epam.pmt.exceptions.NoGroupFoundForAccount;
import com.epam.pmt.exceptions.NoRecordFoundForGroup;
import com.epam.pmt.repository.AccountRepository;

@Service
public class PasswordManagementServiceImpl implements PasswordManagementService {
	@Autowired
	EncryptDecryptPassword encryptDecryptPassword;
	@Autowired
	EncryptionAndDecryption encrypt;
	@Autowired
	AccountRepository accountRepository;
	@Override
	public List<Account> getAccountByGroupName(String groupName,MasterUser current) throws NoGroupFoundForAccount {
		List<Account> accountDetailsList = null;
			accountDetailsList = accountRepository.findByGroupAndMasterUser(groupName,
					current);
			if (accountDetailsList.isEmpty()) {
				throw new NoGroupFoundForAccount("No group present with that name");
			}
		return accountDetailsList;
	}

	@Override
	public boolean deleteAccountByGroup(String groupName,MasterUser current) throws NoRecordFoundForGroup {
		List<Account> deleteAccount=accountRepository.findByGroupAndMasterUser(groupName, current);
		boolean isDeleted=false;
			if(deleteAccount.isEmpty()) {
				throw new NoRecordFoundForGroup("No Records present with that name");
		}else {
			accountRepository.deleteByGroupAndMasterUser(groupName, current);
			isDeleted=true;
		}
		return isDeleted;

	}

	@Override
	public boolean deleteAccountByUrl(String url,MasterUser current) throws InvalidUrlException {
		List<Account> deleteAccount=accountRepository.findByUrlAndMasterUser(url, current);
		boolean isDeleted=false;
			if(deleteAccount.isEmpty()) {
				throw new InvalidUrlException("Please Enter valid url.");
		}else {
			accountRepository.deleteByUrlAndMasterUser(url, current);
			isDeleted=true;
		}
		return isDeleted;
	}

	@Override
	public String getPassword( String accountUsername,MasterUser current) throws AccountDoesNotExistException {
		String decryptPassword = null;
			decryptPassword = encrypt.getDecryptedPassword(accountRepository.findByUsernameAndMasterUser(accountUsername, current),  accountUsername);
			if (decryptPassword.isEmpty()) {
				throw new AccountDoesNotExistException("Oops! No account is present");
			}
			
		return decryptPassword;
	}

	@Override
	public boolean addAccount(Account account,MasterUser current) throws  LoginFirstException{
		boolean isAdded=false;
		account.setMasterUser(current);
		if (current == null) {
			throw new LoginFirstException("Please login first!");

		}else {
		accountRepository.save(account);
		isAdded=true;
		}
		return isAdded;
	}

	@Override
	public List<Account> viewAllAccounts(MasterUser current) throws  AccountDoesNotExistException{
		List<Account> accountDetailsList = null;
			accountDetailsList = accountRepository.findByMasterUser(current);

			if (accountDetailsList.isEmpty()) {
				throw new AccountDoesNotExistException("No Accounts present with that user name");
			}
		return accountDetailsList;
	}

	@Override
	public Account getAccountById(int id) {
		Optional<Account> accountDetails = accountRepository.findById(id);
		Account account = null;
		if (accountDetails.isPresent()) {
			account = accountDetails.get();
		}
		return account;
	}

	
	@Override
	public Account updateAccount(Account account,MasterUser current) {
		account.setMasterUser(current);
		return accountRepository.save(account);
		
	}

	
}
