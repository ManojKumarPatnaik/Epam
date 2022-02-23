//package com.epam.pmt.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.epam.pmt.dto.AccountDto;
//import com.epam.pmt.encryptdecryptpassword.EncryptDecryptPassword;
//import com.epam.pmt.encryptdecryptpassword.EncryptionAndDecryption;
//import com.epam.pmt.entity.Account;
//import com.epam.pmt.servicelayer.PasswordManagementServiceImpl;
//import com.epam.pmt.utils.ConstantsUtils;
//import com.epam.pmt.validation.AccountValidation;
//import com.epam.pmt.validation.ValidateUserName;
//import com.epam.pmt.validation.ValidationPassword;
//@RestController
//@RequestMapping("/pmt")
//public class AccountRestController {
//	
//	@Autowired
//	PasswordManagementServiceImpl passwordManagementServiceImpl;
//	@Autowired
//	AccountValidation accountValidation;
//	@Autowired
//	EncryptionAndDecryption encryptionAndDecryption;
//	@Autowired
//	EncryptDecryptPassword encryptDecryptPassword;
//	@Autowired
//	ValidateUserName validateUserName;
//	@Autowired
//	ValidationPassword validationPassword;
//	HttpHeaders header=new HttpHeaders();
//	
//	@GetMapping("/viewaccounts")
//	public  ResponseEntity<List<Account>> viewAccounts() {
//		
//
//		return new ResponseEntity<>(passwordManagementServiceImpl.viewAllAccounts(),HttpStatus.OK);
//	}
//	
//	@PostMapping("/viewaccounts")
//	public ResponseEntity<Boolean> createAccount(@RequestBody AccountDto accountDto) {
//		boolean isAdded=false;
//	
//		if (accountValidation.isValidURL(accountDto.getUrl()) && accountValidation.isValidGroupName(accountDto.getGroup())
//				&& validateUserName.isValidUserName(accountDto.getUsername())
//				&& validationPassword.validPassword(accountDto.getPassword())) {
//
//			String encrypt = encryptDecryptPassword.encrypt(accountDto.getPassword());
//			Account account1 = new Account(accountDto.getUrl(), accountDto.getUsername(), encrypt, accountDto.getGroup());
//			isAdded=passwordManagementServiceImpl.addAccount(account1);
//			header.add(ConstantsUtils.HEADER,	"Account Created Successfully!");
//		}
//		else {
//			header.add(ConstantsUtils.HEADER,	"Please Enter valid information to create an account ");
//		}
//		return new ResponseEntity<>(isAdded,header,isAdded? HttpStatus.CREATED: HttpStatus.NOT_ACCEPTABLE);
//	}
//	
//	
//	@DeleteMapping("/deletegroup/{group}")
//	public  ResponseEntity<Boolean>  deleteGroup(@PathVariable String group) {
//		boolean isDeleted=false;
//		if(passwordManagementServiceImpl.deleteAccountByGroup(group)) {
//			isDeleted=true;
//			header.add(ConstantsUtils.HEADER, "Account group deleted Successfully");
//			
//		}else {
//			header.add(ConstantsUtils.HEADER,	"Please Enter valid group name.");
//		}
//
//		return new ResponseEntity<>(isDeleted,header,isDeleted? HttpStatus.OK: HttpStatus.NOT_ACCEPTABLE);
//	}
//	
//	@PostMapping("/deleteurl")
//	public  ResponseEntity<Boolean>  deleteUrl(@RequestBody AccountDto accountDto) {
//		boolean isDeleted=false;
//		if(passwordManagementServiceImpl.deleteAccountByUrl(accountDto.getUrl())) {
//			isDeleted=true;
//			header.add(ConstantsUtils.HEADER, "Account url deleted Successfully");
//			
//		}else {
//			header.add(ConstantsUtils.HEADER,	"Please Enter valid url.");
//		}
//
//		return new ResponseEntity<>(isDeleted,header,isDeleted? HttpStatus.OK: HttpStatus.NOT_ACCEPTABLE);
//	}
//	
//	
//	
//	@PostMapping("/sortbygroup")
//	public  ResponseEntity<List<Account>>  sortByGroup(@RequestBody AccountDto accountDto) {
//		List<Account> account=passwordManagementServiceImpl.getAccountByGroupName(accountDto.getGroup());
//		if(account.isEmpty()) {
//			header.add(ConstantsUtils.HEADER,	"Please Enter valid group name.");
//		}
//			header.add(ConstantsUtils.HEADER, "Results found on bases of group name.");
//
//		return new ResponseEntity<>(account,header, HttpStatus.OK);
//	}
//	
//	@PostMapping("/readpassword")
//	public  ResponseEntity<Object>  getPassword(@RequestBody AccountDto accountDto) {
//		String password = passwordManagementServiceImpl.getPassword( accountDto.getUsername());
//		
//		
//		
//			header.add(ConstantsUtils.HEADER, "Password Decrypted Successfully.");
//		
//		return new ResponseEntity<>(password==null?"Please Enter valid username.":password,header, HttpStatus.OK);
//	}
//	
//	@PutMapping("/updateaccount/{accountId}")
//	public ResponseEntity<Object>  doEditAccount(@PathVariable("accountId") int id, @RequestBody AccountDto acountDto) {
//		Account account =null ;
//		boolean isUpdated=false;
//			if(accountValidation.isValidURL(acountDto.getUrl())&&
//			accountValidation.isValidGroupName(acountDto.getGroup())&&
//			validateUserName.isValidUserName(acountDto.getUsername())&&
//			validationPassword.validPassword(acountDto.getPassword()) && passwordManagementServiceImpl.getAccountById(id)!=null){
//				isUpdated=true;
//			String encryptPassword = encryptDecryptPassword.encrypt(acountDto.getPassword());
//			account=passwordManagementServiceImpl.getAccountById(id);
//			account = new Account(id,acountDto.getUrl(),acountDto.getUsername(),encryptPassword,acountDto.getGroup());
//			account=passwordManagementServiceImpl.updateAccount(account);
//			header.add(ConstantsUtils.HEADER, "Account Updated Successfully!");
//			}
//		
//		return  new ResponseEntity<>(isUpdated?account:"Records no found",header, HttpStatus.OK);
//	}
//	
//	
//	
//	
//
//}
