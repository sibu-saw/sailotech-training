package com.sailotech.assessment.second.noushad;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class AccountSystem {
	private Map<String, Account> accounts = new HashMap<>();
	private Scanner scanner = new Scanner(System.in);
	private static Account account;

	public static void main(String[] args) {
		AccountSystem system = new AccountSystem();
		system.run();
	}

	public void run() {
		while (true) {
			System.out.println("1. Create an account");
			System.out.println("2. Log into an account");
			System.out.println("3. edit an account");
			System.out.println("4. Post a message");
			System.out.println("5. Reply to a message");
			System.out.println("6. Exit");
			System.out.print("Select an option: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume the newline
			switch (choice) {
			case 1:
				createAccount();
				break;
			case 2:
				logIntoAccount();
				break;
			case 3:
				editAccount(account);
				break;
			case 4:
				postMessage(account);
				break;
			case 5:
				replyToMessage(account);
				break;
			case 6:
				System.out.println("Exiting...");
				return;
			default:
				System.out.println("Invalid choice");
			}
		}
	}

	/**
	 * @author sibu
	 * Database interaction missing
	 * 
	 * On an invalid input, stay at the same menu item allowing the user to re-enter
	 * Incorrect validation messages
	 * 
	 */
//creating an account
	private void createAccount() {
		String phoneNumber = getValidPhoneNumber();
//check the account already exits or not 
		if (accounts.containsKey(phoneNumber)) {
			System.out.println("Account already exists");
			return;
		}
// giving input details of account
		String firstName = getValidName("first");
		String lastName = getValidName("last");
		String password = getValidPassword();
		Account account = new Account(firstName, lastName, phoneNumber, password);
		accounts.put(phoneNumber, account);
		System.out.println("Account created successfully");
	}

//validate the phonenumber
	private String getValidPhoneNumber() {
		while (true) {
			System.out.print("Enter phone number: ");
			String phoneNumber = scanner.nextLine();
			if (Pattern.matches("\\d{10}", phoneNumber)) {
				return phoneNumber;
			} else {
				System.out.println("Invalid input");
			}
		}
	}

//validate the name
	private String getValidName(String nameType) {
		while (true) {
			System.out.print("Enter " + nameType + " name: ");
			String name = scanner.nextLine();
			if (Pattern.matches("[a-zA-Z]{1,20}", name)) {
				return name;
			} else {
				System.out.println("Invalid input");
			}
		}
	}

//validate the password
	private String getValidPassword() {
		while (true) {
			System.out.print("Enter password: ");
			String password = scanner.nextLine();
			if (Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,12}$", password)) {
				return password;
			} else {
				System.out.println(
						"Invalid input. Password should contain at least one alphabet, one numeric, one special character, and be between 8 and 12 characters long.");
			}
		}
	}

	/**
	 * @author sibu
	 * Database interaction missing
	 * 
	 * On an invalid input, stay at the same menu item allowing the user to re-enter
	 * Incorrect validation messages
	 * Why is the user being take to edit immediately after login??
	 * 
	 */
//log into account 
	private void logIntoAccount() {
		String phoneNumber = getValidPhoneNumber();
//checking the account exits or not
		if (!accounts.containsKey(phoneNumber)) {
			System.out.println("Account does not exist");
			return;
		}
		Account account = accounts.get(phoneNumber);
		String password = getValidPassword();
		if (!account.checkPassword(password, phoneNumber)) {
			System.out.println("Incorrect phone number or password");
			return;
		}
		System.out.println("Logged in successfully");
		editAccount(account);
	}

	/**
	 * @author sibu
	 * Database interaction missing
	 * 
	 */
//editing an account details
	private void editAccount(Account account) {
		String newFirstName = getValidName("new first");
		String newLastName = getValidName("new last");
		String newPassword = getValidPassword();
		account.setFirstName(newFirstName);
		account.setLastName(newLastName);
		account.setPassword(newPassword);
		System.out.println("Account updated successfully");
	}

	
	/**
	 * @author sibu
	 * Database interaction missing
	 * 
	 * If not in the database, the message should have held in memory
	 */
//posting a message to another user
	private void postMessage(Account sender) {
		String recipientPhoneNumber = getValidPhoneNumber();
		if (!accounts.containsKey(recipientPhoneNumber)) {
			System.out.println("Recipient does not exist");
			return;
		}
		Account recipient = accounts.get(recipientPhoneNumber);
		String message = getValidMessage();
		System.out.println("Message sent successfully to recipient: " + recipientPhoneNumber);
	}

// validating the message
	private String getValidMessage() {
		while (true) {
			System.out.print("Enter message: ");
			String message = scanner.nextLine().trim();
			if (!message.isEmpty()) {
				return message;
			} else {
				System.out.println("Invalid input");
			}
		}
	}

	/**
	 * @author sibu
	 * Invalid implementation
	 */
// giving reply to a message
	private void replyToMessage(Account sender) {
		String recipientPhoneNumber = getValidPhoneNumber();
		if (!accounts.containsKey(recipientPhoneNumber)) {
			System.out.println("Recipient does not exist");
			return;
		}
		Account recipient = accounts.get(recipientPhoneNumber);
		System.out.println("messages from recipient:");
		int messageCounter = 0;
		for (String message : recipient.getMessages()) {
			messageCounter++;
			System.out.println(messageCounter + ". " + message);
		}
		int messageNumber = getValidMessageNumber(messageCounter);
		String replyMessage = getValidMessage();
		System.out.println("Message replied successfully to recipient: " + recipientPhoneNumber);
	}

//validating the messages
	private int getValidMessageNumber(int maxNumber) {
		while (true) {
			System.out.print("Select a message number: ");
			int messageNumber = scanner.nextInt();
			scanner.nextLine(); // Consume the newline
			if (messageNumber >= 1 && messageNumber <= maxNumber) {
				return messageNumber;
			} else {
				System.out.println("Invalid input");
			}
		}
	}
}
