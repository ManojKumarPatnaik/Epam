package com.epam.pmt.presentationlayer;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.pmt.entity.MasterUser;
import com.epam.pmt.servicelayer.CrudServiceImpl;

@Component
public class OptionMenu {
	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(OptionMenu.class);
	private static final Scanner scanner = new Scanner(System.in);
	@Autowired
	CreateAccount create;
	@Autowired
	CrudServiceImpl crudServiceImpl;

	public void menu() {
		MasterUser currentUser = CurrentAccount.getInstance().getCurrentUser();
		try {
			int option2 = 0;
			while (true) {

				logger.info("Enter number for Respective option");
				logger.info("1.Create Password for an account");
				logger.info("2.Read Password");
				logger.info("3.List Password account and Groups");
				logger.info("4.Delete Password account");
				logger.info("5.Modify/Update password account");
				logger.info("6.Modify Group details");
				logger.info("7.Delete Group");
				logger.info("8.Logout");
				option2 = validOption(option2);
				if ((option2 < 8)) {
					switch (option2) {
					case 1:
						create.accountService(currentUser);

						break;
					case 2:
						logger.info("Enter account url");
						String accountUrl = scanner.next();
						logger.info("Enter user name");
						String accountUsername = scanner.next();
						crudServiceImpl.getPassword(accountUrl, accountUsername);
						break;
					case 3:
						logger.info("Enter group:");
						String group = scanner.next();
						crudServiceImpl.getAccountByGroupName(group);
						break;
					case 4:
						logger.info("Enter Account Url to delete password account");
						String url = scanner.next();
						crudServiceImpl.deleteAccountByUrl(url);
						break;
					case 5:
						int option5;
						do {
							logger.info("Choose option");
							logger.info("1.Update Password");
							logger.info("2.Update AccountURL");
							logger.info("3.<-Back");
							option5 = scanner.nextInt();
							if (option5 == 1) {
								logger.info("Enter url");
								String url1 = scanner.next();
								logger.info("Enter the new Password");
								String newpassword = scanner.next();
								crudServiceImpl.modifyPassword(url1, newpassword);
							} else if (option5 == 2) {
								logger.info("Enter url");
								String url1 = scanner.next();
								logger.info("Enter new url");
								String newUrl1 = scanner.next();
								crudServiceImpl.modifyUrl(url1, newUrl1);
							} else {
								break;
							}
						} while (option5 != 3);
						break;
					case 6:
						logger.info("Enter old group name ");
						String group1 = scanner.next();
						logger.info("Enter new group name ");
						String newGroup = scanner.next();
						crudServiceImpl.modifyGroupDetails(group1, newGroup);
						break;
					case 7:
						logger.info("Enter old group name ");
						String groupName = scanner.next();
						crudServiceImpl.deleteAccountByGroup(groupName);
						break;
					default:
					    break;


					}

				}
				if (option2 == 8) {
					logger.warn("Logged off Succussfully \n ");

					break;
				}
			} 
		} catch (Exception message) {
			logger.warn("Invalid option.");
		}
	}

	private int validOption(int option2) {
		try {
			option2 = scanner.nextInt();
		} catch (Exception e) {
			scanner.next();
			menu();
		}
		return option2;
	}

}
