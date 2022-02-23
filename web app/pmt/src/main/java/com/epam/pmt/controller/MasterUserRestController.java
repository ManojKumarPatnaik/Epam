//package com.epam.pmt.controller;
//
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.epam.pmt.dto.MasterUserDto;
//import com.epam.pmt.entity.MasterUser;
//import com.epam.pmt.repo.MasterUserRepo;
//import com.epam.pmt.servicelayer.PasswordManagementServiceImpl;
//import com.epam.pmt.utils.CurrentAccount;
//import com.epam.pmt.utils.Login;
//import com.epam.pmt.validation.ValidateUserName;
//import com.epam.pmt.validation.ValidationPassword;
//
//@RestController
//@RequestMapping("/pmt")
//public class MasterUserRestController {
//	@Autowired
//	PasswordManagementServiceImpl passwordManagementServiceImpl;
//	@Autowired
//	ValidateUserName validateUserName;
//	@Autowired
//	ValidationPassword validationPassword;
//	@Autowired
//	Login login;
//	MasterUser currentUser;
//	HttpHeaders headers=new HttpHeaders();
//	@Autowired
//	MasterUserRepo masterUserRepo;
//	
//	@PostMapping("/")
//	public ResponseEntity<Object> registration(@RequestBody MasterUserDto masterUserDto) 
//	{
//		
//		MasterUser masterUsersave =  passwordManagementServiceImpl.addUser(new MasterUser(masterUserDto.getUserid(),masterUserDto.getUsername(),masterUserDto.getPassword()));
//		return new ResponseEntity<> (masterUsersave!=null?masterUsersave:"Invalid Master User Details",HttpStatus.OK);
//	}
//
//	
//	
//	
//	@PostMapping("/login")
//	public ResponseEntity<Boolean> doLogin(@RequestBody MasterUserDto masterUserDto) {
//		boolean isValid=false;
//		
//		if(login.validateLoginDetails(masterUserDto.getUsername(), masterUserDto.getPassword())) {
//			isValid=login.validateLoginDetails(masterUserDto.getUsername(), masterUserDto.getPassword());
//		passwordManagementServiceImpl.setCurrentAccount(masterUserDto.getUsername(), masterUserDto.getPassword());
//			headers.add("headers"," Login Successfully");
//		}else {
//			
//			headers.add("headers","Invalid Login credentails");
//		}
//		return new ResponseEntity<>(isValid,headers,isValid? HttpStatus.OK : HttpStatus.NOT_FOUND);
//	}
//	
//	@GetMapping("/users")
//	public  ResponseEntity<List<MasterUser>> viewAllMasterUsers() 
//	{
//		return new ResponseEntity<>(passwordManagementServiceImpl.viewAllUsers(),HttpStatus.OK);
//	}
//	
//	
//	@GetMapping("/logout")
//	public ResponseEntity<String> logOut() {
//		CurrentAccount.getInstance().setCurrentUser(null);
//		return  new ResponseEntity<>(CurrentAccount.getInstance().getCurrentUser()==null?"LogOut Successfully!":"TryAgain",HttpStatus.OK);
//	}
//	
//}
