package com.epam.pmt.database;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.pmt.entity.Account;
import com.epam.pmt.entity.MasterUser;
import com.epam.pmt.presentationlayer.CurrentAccount;
import com.epam.pmt.repo.AccountRepo;
import com.epam.pmt.repo.MasterUserRepo;
import com.epam.pmt.utils.ConstantsUtils;

@Repository
public class CrudOperationsDbImpl  {
	@Autowired
	AccountRepo  accountRepo;
	@Autowired
	MasterUserRepo masterUserRepo;
	

	public List<Account> getAccountList(MasterUser currentMasterUser) {
//		MasterUser user=CurrentAccount.getInstance().getCurrentUser();
		return accountRepo.findByMasterUser(currentMasterUser);

	}

	public List<MasterUser> getAllUsers() {
		return (List<MasterUser>) masterUserRepo.findAll();

	}
	
	public boolean addMasterUser(MasterUser masterUser) {
		
		masterUserRepo.save(masterUser);
		return true;
	}

	public List<Account> listAccountByGroupName( String group,MasterUser currentMasterUser) {
//		MasterUser user=CurrentAccount.getInstance().getCurrentUser();

		return accountRepo.sortByByGroupName(group, currentMasterUser);
	}

	
	public boolean deleteAccountByGroupName( String group,MasterUser currentMasterUser) {
//		MasterUser user=CurrentAccount.getInstance().getCurrentUser();
		accountRepo.deleteByGroupAndMasterUser(group, currentMasterUser);
		return true;
	}

	


	public boolean deleteAccountByUrl( String url,MasterUser currentMasterUser) {
//		MasterUser user=CurrentAccount.getInstance().getCurrentUser();
		accountRepo.deleteByUrlAndMasterUser(url, currentMasterUser);
		return true;
	}

	public List<Account> getDecryptedPasswordList(  String username,MasterUser currentMasterUser) {
//		MasterUser user=CurrentAccount.getInstance().getCurrentUser();
		List<Account> account;
		account = accountRepo.findByUsernameAndMasterUser(username, currentMasterUser);
		return account;
	}
	public boolean add(Account account) {
		accountRepo.save(account);
		ConstantsUtils.logger.info(account);
		return true;
	}
	
	
	public void  updateAccountDetails(int id,String url,String username, String password,String group){
		accountRepo.updateAccountDetails(id, url, username, password, group);
	}

	public Account getAccountById(int id) {
		Optional<Account>	accountDetails=	accountRepo.findById(id);
		Account account = null;
		if(accountDetails.isPresent()) {
			 account=accountDetails.get();
		}
		return account;
	}

}
