package com.epam.pmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.pmt.encryptdecryptpassword.EncryptDecryptPassword;
import com.epam.pmt.encryptdecryptpassword.EncryptionAndDecryption;
import com.epam.pmt.entity.Account;
import com.epam.pmt.exceptions.AccountDoesNotExistException;
import com.epam.pmt.exceptions.InvalidGroupNameException;
import com.epam.pmt.exceptions.InvalidUrlException;
import com.epam.pmt.exceptions.LoginFirstException;
import com.epam.pmt.exceptions.NoGroupFoundForAccount;
import com.epam.pmt.exceptions.NoRecordFoundForGroup;
import com.epam.pmt.presentationlayer.CurrentAccount;
import com.epam.pmt.servicelayer.CrudServiceImpl;
import com.epam.pmt.validation.AccountValidation;

@Controller
public class AccountController {
	@Autowired
	CrudServiceImpl crudServiceImpl;
	@Autowired
	AccountValidation validation;
	@Autowired
	EncryptionAndDecryption encryptionAndDecryption;
	@Autowired
	EncryptDecryptPassword encryptDecryptPassword;
	private static final String ACTION_1 = "accounts";

	private static final String DELETE_GROUP = "deletegroup";
	private static final String MESSAGE = "message";
	private static final String MODIFY_GROUP = "modifygroup";
	private static final String MODIFY_URL = "modifyurl";
	private static final String SORT_BY_GROUP = "sortbygroup";
	private static final String VIEW_DETAILS = "viewAccount";
	private static final String REDIRECT_VIEW = "redirect:/viewAccount";
	private static final String REDIRECT_WELCOME = "redirect:/welcome";
	private static final String LOGIN = "Please Login First";
	private static final String WELCOME = "welcome";

	@GetMapping("/createAccount")
	public ModelAndView createAccount(@ModelAttribute("account") Account account) {
		ModelAndView modelAndView = new ModelAndView();
		if (CurrentAccount.getInstance().getCurrentUser() == null) {
			modelAndView.setViewName(REDIRECT_WELCOME);
			modelAndView.addObject(MESSAGE, LOGIN);
		} else {
			modelAndView.setViewName("createAccount");
		}
		return modelAndView;
	}

	@PostMapping("/createAccount")
	public ModelAndView doCreateAccount(@ModelAttribute("account") Account account) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (CurrentAccount.getInstance().getCurrentUser() == null) {
				throw new LoginFirstException(LOGIN);
			}
			validation.isValidURL(account.getUrl());
			validation.isValidGroupName(account.getGroup());
			String encrypt = encryptDecryptPassword.encrypt(account.getPassword());
			account = new Account(account.getUrl(), account.getUsername(), encrypt, account.getGroup());
			crudServiceImpl.addAccount(account);
			modelAndView.setViewName(REDIRECT_VIEW);
			modelAndView.addObject(MESSAGE, "Account Created Successfully!");
		} catch (InvalidUrlException | InvalidGroupNameException | LoginFirstException e) {
			modelAndView.addObject(MESSAGE, e.getMessage());
			modelAndView.setViewName("createAccount");
		}
		return modelAndView;
	}

	@GetMapping("/viewAccount")
	public ModelAndView viewAccount() {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (CurrentAccount.getInstance().getCurrentUser() == null) {
				throw new LoginFirstException(LOGIN);
			}
			modelAndView.setViewName(VIEW_DETAILS);
			modelAndView.addObject(ACTION_1, crudServiceImpl.viewAllAccounts());

			modelAndView.addObject(MESSAGE, "Displaying All Accounts");
		} catch (AccountDoesNotExistException | LoginFirstException e) {
			modelAndView.addObject(MESSAGE, e.getMessage());
			modelAndView.setViewName(WELCOME);
		}
		return modelAndView;
	}

	@GetMapping("/menu")
	public ModelAndView menu() {
		ModelAndView modelAndView = new ModelAndView();
		if (CurrentAccount.getInstance().getCurrentUser() == null) {
			modelAndView.setViewName(REDIRECT_WELCOME);
			modelAndView.addObject(MESSAGE, LOGIN);
		} else {
			modelAndView.setViewName("menu");
		}
		return modelAndView;
	}

	@RequestMapping("deletegroup")
	public ModelAndView deletegroup(String group) {
		ModelAndView modelAndView = new ModelAndView();

		try {
			if (CurrentAccount.getInstance().getCurrentUser() == null) {
				modelAndView.setViewName(REDIRECT_WELCOME);
				modelAndView.addObject(MESSAGE, LOGIN);
			} else {
				modelAndView.setViewName(DELETE_GROUP);
				modelAndView.addObject(MESSAGE, "Choose the Group Name want to delete");

				modelAndView.addObject(ACTION_1, crudServiceImpl.viewAllAccounts());
				if (!modelAndView.isEmpty() && crudServiceImpl.deleteAccountByGroup(group)) {
					modelAndView.addObject(ACTION_1, crudServiceImpl.viewAllAccounts());
					modelAndView.addObject("errorMessage", "Account group deleted Successfully");
					modelAndView.setViewName(REDIRECT_VIEW);
				}
			}
		} catch (NoRecordFoundForGroup | AccountDoesNotExistException message) {
			modelAndView.setViewName(DELETE_GROUP);
		}

		return modelAndView;
	}

	@RequestMapping("/deleteUrl")
	public ModelAndView deleteByUrl(String url) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (CurrentAccount.getInstance().getCurrentUser() == null) {
				modelAndView.setViewName(REDIRECT_WELCOME);
				modelAndView.addObject(MESSAGE, LOGIN);
			} else {
				modelAndView.setViewName(VIEW_DETAILS);
				modelAndView.addObject(MESSAGE, "Choose the Group Name want to delete");

				modelAndView.addObject(ACTION_1, crudServiceImpl.viewAllAccounts());
				if (!modelAndView.isEmpty() && crudServiceImpl.deleteAccountByUrl(url)) {
					modelAndView.addObject(ACTION_1, crudServiceImpl.viewAllAccounts());
					modelAndView.addObject(MESSAGE, "Account deleted Successfully");
					modelAndView.setViewName(VIEW_DETAILS);
				}
				modelAndView.setViewName(VIEW_DETAILS);
			}
		} catch (InvalidUrlException | AccountDoesNotExistException message) {
			modelAndView.addObject(MESSAGE, "no Group Name want to delete");
			modelAndView.addObject(ACTION_1, crudServiceImpl.viewAllAccounts());
			modelAndView.setViewName(VIEW_DETAILS);
		}

		return modelAndView;
	}

	@RequestMapping("/update")
	public ModelAndView update(@ModelAttribute("account") Account account) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (CurrentAccount.getInstance().getCurrentUser() == null) {
				modelAndView.setViewName(REDIRECT_WELCOME);
				modelAndView.addObject(MESSAGE, LOGIN);
			} else {
				modelAndView.setViewName("update");
				List<Account> newlist = crudServiceImpl.modifyPassword(account.getUrl(), account.getPassword());
				if (!newlist.isEmpty()) {
					modelAndView.addObject(MESSAGE, "Password is Updated successfully");
					modelAndView.setViewName("menu");
				}
			}
		} catch (Exception e) {
			modelAndView.setViewName("update");
		}

		return modelAndView;

	}

	@RequestMapping("/read")
	public ModelAndView readPasswrd(@ModelAttribute("account") Account account, String url, String username) {
		ModelAndView modelAndView = new ModelAndView();

		try {
			if (CurrentAccount.getInstance().getCurrentUser() == null) {
				modelAndView.setViewName(REDIRECT_WELCOME);
				modelAndView.addObject(MESSAGE, LOGIN);
			} else {
				modelAndView.setViewName("read");
				String password = crudServiceImpl.getPassword(url, username);
				if (!password.isEmpty()) {
					modelAndView.addObject(MESSAGE, "The Decrypted password : " + password);
				}
			}
		} catch (InvalidUrlException e) {
			modelAndView.addObject("deleteMessage", e.getMessage());
			modelAndView.setViewName("read");
		}
		return modelAndView;

	}

	@GetMapping("/modifyurl")
	public ModelAndView modifyUrlGet(String url, String newUrl) {
		ModelAndView modelAndView = new ModelAndView();
		if (CurrentAccount.getInstance().getCurrentUser() == null) {
			modelAndView.setViewName(REDIRECT_WELCOME);
			modelAndView.addObject(MESSAGE, LOGIN);
		} else {
			modelAndView.setViewName(MODIFY_URL);
		}
		return modelAndView;

	}

	@PostMapping("/modifyurl")
	public ModelAndView modifyUrl(String url, String newUrl) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (CurrentAccount.getInstance().getCurrentUser() == null) {
				modelAndView.setViewName(REDIRECT_WELCOME);
				modelAndView.addObject(MESSAGE, LOGIN);
			} else {
				modelAndView.setViewName(MODIFY_URL);
				if (crudServiceImpl.modifyUrl(url, newUrl)) {
					modelAndView.addObject(MESSAGE, "URL is Updated successfully");
					modelAndView.setViewName("menu");
				}
			}
		} catch (InvalidUrlException e) {
			modelAndView.addObject(MESSAGE, e.getMessage());
			modelAndView.setViewName(MODIFY_URL);
		}

		return modelAndView;

	}

	@GetMapping("/modifygroup")
	public ModelAndView modifyGroup(String group, String newgroup) {
		ModelAndView modelAndView = new ModelAndView();
		if (CurrentAccount.getInstance().getCurrentUser() == null) {
			modelAndView.setViewName(REDIRECT_WELCOME);
			modelAndView.addObject(MESSAGE, LOGIN);
		} else {
			modelAndView.setViewName(MODIFY_GROUP);
		}
		return modelAndView;

	}

	@PostMapping("/modifygroup")
	public ModelAndView doModifyGroup(String group, String newgroup) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.setViewName(MODIFY_GROUP);
			if (!crudServiceImpl.modifyGroupDetails(group, newgroup).isEmpty()) {
				modelAndView.addObject(MESSAGE, "Group is Updated successfully");
				modelAndView.setViewName("menu");
			}
		} catch (NoGroupFoundForAccount e) {
			modelAndView.addObject(MESSAGE, e.getMessage());
			modelAndView.setViewName(MODIFY_GROUP);
		}

		return modelAndView;

	}

	@GetMapping("/sortbygroup")
	public ModelAndView sortbygroup(String group) {
		ModelAndView modelAndView = new ModelAndView();
		if (CurrentAccount.getInstance().getCurrentUser() == null) {
			modelAndView.setViewName(REDIRECT_WELCOME);
			modelAndView.addObject(MESSAGE, LOGIN);
		} else {
			modelAndView.setViewName(SORT_BY_GROUP);
		}
		return modelAndView;

	}

	@PostMapping("/sortbygroup")
	public ModelAndView dosortbygroup(String group) {
		ModelAndView modelAndView = new ModelAndView();
		try {

			modelAndView.setViewName(SORT_BY_GROUP);
			List<Account> newlist = crudServiceImpl.getAccountByGroupName(group);
			if (!newlist.isEmpty()) {
				modelAndView.addObject(MESSAGE, "Results found on bases of group name.");
				modelAndView.addObject(ACTION_1, newlist);
				modelAndView.setViewName(VIEW_DETAILS);
			}

		} catch (NoGroupFoundForAccount e) {
			modelAndView.addObject(MESSAGE, e.getMessage());
			modelAndView.setViewName(SORT_BY_GROUP);
		}

		return modelAndView;

	}

	@GetMapping("/edit/{id}")
	public ModelAndView editAccount(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView("edit");
		if (CurrentAccount.getInstance().getCurrentUser() == null) {
			modelAndView.setViewName(REDIRECT_WELCOME);
			modelAndView.addObject(MESSAGE, LOGIN);
		} else {
			modelAndView.setViewName("edit");
			modelAndView.addObject("account", crudServiceImpl.getAccountById(id));
		}
		return modelAndView;
	}

	@PostMapping("/edit/{id}")
	public ModelAndView doEditAccount(@PathVariable int id, @ModelAttribute("account") Account account) {
		ModelAndView modelAndView = new ModelAndView();
		try {

			validation.isValidURL(account.getUrl());
			validation.isValidGroupName(account.getGroup());
			crudServiceImpl.updateAccount(account);
			modelAndView.setViewName(REDIRECT_VIEW);
			modelAndView.addObject(MESSAGE, "Account Updated Successfully!");
		} catch (InvalidUrlException | InvalidGroupNameException e) {
			modelAndView.addObject(MESSAGE, e.getMessage());
			modelAndView.setViewName("edit");
		}
		return modelAndView;
	}

}
