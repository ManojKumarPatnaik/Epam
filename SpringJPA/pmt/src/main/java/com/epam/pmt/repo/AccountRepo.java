package com.epam.pmt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.epam.pmt.entity.Account;
import com.epam.pmt.entity.MasterUser;
@Transactional
public interface AccountRepo extends CrudRepository<Account, Integer> {

	List<Account> findByMasterUser(MasterUser masterUser);

	
	@Modifying
	@Query("update Account account set account.url=?2, account.username=?3, account.password=?4, account.group=?5 where account.accountId=?1")
	void updateAccountDetails(int id, String url, String username, String password, String group);

	List<Account> findByUsernameAndMasterUser(String username, MasterUser user);

	@Query("select account from Account account where account.group=?1 and account.masterUser=?2")
	List<Account> sortByByGroupName(String group, MasterUser user);

	
	@Modifying
	List<Account>  deleteByUrlAndMasterUser(@Param("url") String url, @Param("user") MasterUser user);
	
	
	@Modifying
	void deleteByGroupAndMasterUser(String group, MasterUser user);

}
