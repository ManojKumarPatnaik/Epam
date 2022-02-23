package com.epam.pmt.servicelayer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.pmt.entity.MasterUser;
import com.epam.pmt.exceptions.InvalidLoginDetail;
import com.epam.pmt.exceptions.MasterUserAlreadyExistsException;
import com.epam.pmt.exceptions.MasterUsersDoesNotExistException;
import com.epam.pmt.repository.MasterUserRepository;
@Service
public class UserServiceImpl  implements UserService{
	@Autowired
	MasterUserRepository masterUserRepository;
	
	@Override
	public List<MasterUser> viewAllUsers() throws MasterUsersDoesNotExistException {

		List<MasterUser> usersDetailsList = (List<MasterUser>) masterUserRepository.findAll();

		if (usersDetailsList.isEmpty()) {
			throw new MasterUsersDoesNotExistException("No Users present in database. ");
		}

		return usersDetailsList;
	}
	@Override
	public MasterUser addUser(MasterUser user) throws MasterUserAlreadyExistsException{
		MasterUser masterUser=null;
		if (masterUserRepository.existsByUsername(user.getUsername())) {
			throw new MasterUserAlreadyExistsException("MasterUser Already Exists");
		}else {
			masterUser=masterUserRepository.save(user);
		}
		
		return masterUser;
	}
	@Override
	public MasterUser getMasterUserById(int id) {
		Optional<MasterUser> userDetails = masterUserRepository.findById(id);
		MasterUser user = null;
		if (userDetails.isPresent()) {
			user = userDetails.get();
		}
		return user;
	}
	
	@Override
	public boolean validateLoginDetails(String user, String password) throws InvalidLoginDetail {
		boolean isValid=false;
		Optional<MasterUser> currentUser = masterUserRepository.findByUsernameAndPassword(user, password);
		if (currentUser.isPresent()) {
			isValid=true;
		}else {
			throw new InvalidLoginDetail("Invalid login details");
			
		}
		return isValid;
	}

	

}
