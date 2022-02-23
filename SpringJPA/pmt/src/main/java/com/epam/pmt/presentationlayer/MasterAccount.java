package com.epam.pmt.presentationlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.pmt.entity.MasterUser;
import com.epam.pmt.repo.MasterUserRepo;

@Component
public class MasterAccount {

	@Autowired
	MasterUserRepo masterUserRepo;

	public boolean addMasterUser(MasterUser masterUser) {

		masterUserRepo.save(masterUser);
		return true;
	}

}
