package com.sailotech.assessment.second.dinesh;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Account {
	String firstName;
	String lastName;
	String password;

	public Account(String firstName, String lastName, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}
}

public class ConsoleApplication {
	static Scanner scanner = new Scanner(System.in);
	static Map<String, Account> accounts = new HashMap<>();
	static String loggedInPhoneNumber = null;

	public static void main(String[] args) {
		boolean exit = false;
		while (!exit) {
			System.out.println("Menu:");
			System.out.println("1. Create an account");
			System.out.println("2. Log into an account");
			System.out.println("3. Edit an account");
			System.out.println("4. post a message");
			System.out.println("5.reply To Message");
			System.out.println("6.forward Message");
			System.out.println("7. Logout");
			System.out.println("8. Exit Application");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {
			case 1:
				createAccount();
				break;
			case 2:
				login();
				break;
			case 3:
				editAccount();
				break;
			case 4:
				postMessage();
				break;
			case 5:
				replyToMessage();
				break;
			case 6:
				forwardMessage();
				break;
			case 7:
				logout();
				break;
			case 8:
				exit = true;
				System.out.println("Quit the application.");
				break;
			default:
				System.out.println("Invalid choice. Please select a valid option.");
			}
		}
	}
	
	/**
	 * @author sibu
	 * Database interaction missing
	 * 
	 * Incorrect error messages
	 * On an invalid input, stay at the same menu item allowing the user to re-enter
	 * 
	 * Account should have a field to hold the phone number
	 * Why the name isValidName1(), also, it is a duplicate isValidName()
	 */
// Create Account
	public static void createAccount() {
		System.out.println("Creating an account");
		System.out.print("Enter phone number: ");
		String phoneNumber = scanner.nextLine();
		if (!isValidPhoneNumber(phoneNumber)) {
			System.out.println("Invalid input");
			return;
		}
		if (accounts.containsKey(phoneNumber)) {
			System.out.println("Account already exists");
			return;
		}
		System.out.print("Enter first name: ");
		String firstName = scanner.nextLine();
		if (!isValidName1(firstName)) {
			System.out.println("Invalid input");
			return;
		}
		System.out.print("Enter last name: ");
		String lastName = scanner.nextLine();
		if (!isValidName1(lastName)) {
			System.out.println("Invalid input");
			return;
		}
		System.out.print("Enter password: ");
		String password = scanner.nextLine();
		if (!isValidPassword1(password)) {
			System.out.println("Invalid input. Password requirements not met.");
			return;
		}
		accounts.put(phoneNumber, new Account(firstName, lastName, password));
		System.out.println("Account created successfully!");
	}

	public static boolean isValidPhoneNumber(String phoneNumber) {
		return phoneNumber.matches("\\d{10}");
	}

	public static boolean isValidName1(String name) {
		return name.matches("[A-Za-z]{1,20}");
	}

	public static boolean isValidPassword1(String password) {
		return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@!#$%&*(){}\\[\\]]).{8,12}$");
	}

	/**
	 * @author sibu
	 * Database interaction missing
	 * 
	 * Incorrect error messages
	 * On an invalid input, stay at the same menu item allowing the user to re-enter
	 * 
	 * 
	 * Why the name isValidPassword1(), also, it is a duplicate isValidPassword()
	 */
// Login page
	public static void login() {
		System.out.println("Logging into an account");
		System.out.print("Enter phone number: ");
		String phoneNumber = scanner.nextLine();
		if (!isValidPhoneNumber(phoneNumber) || !accounts.containsKey(phoneNumber)) {
			System.out.println("Invalid input or account does not exist");
			return;
		}
		System.out.print("Enter password: ");
		String password = scanner.nextLine();
		if (!isValidPassword1(password) || !accounts.get(phoneNumber).password.equals(password)) {
			System.out.println("Incorrect phone number or password");
			return;
		}
		loggedInPhoneNumber = phoneNumber;
		System.out.println("Login successful!");
	}

	public static boolean isValidPhoneNumber1(String phoneNumber) {
		return phoneNumber.matches("\\d{10}");
	}

	public static boolean isValidName(String name) {
		return name.matches("[A-Za-z]{1,20}");
	}

	public static boolean isValidPassword(String password) {
		return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@!#$%&*(){}\\[\\]]");
	}

	/**
	 * @author sibu
	 * Database interaction missing
	 * 
	 * Incorrect error messages
	 * On an invalid input, stay at the same menu item allowing the user to re-enter
	 * 
	 * Why the name isValidName1(), also, it is a duplicate isValidName()
	 * Why the name isValidName1(), also, it is a duplicate isValidPassword()
	 */	
	public static void editAccount() {
		if (loggedInPhoneNumber == null) {
			System.out.println("You need to be logged in to edit your account.");
			return;
		}
		System.out.println("Editing your account");
		System.out.print("Enter new first name: ");
		String newFirstName = scanner.nextLine();
		if (!isValidName1(newFirstName)) {
			System.out.println("Invalid input");
			return;
		}
		System.out.print("Enter new last name: ");
		String newLastName = scanner.nextLine();
		if (!isValidName1(newLastName)) {
			System.out.println("Invalid input");
			return;
		}
		System.out.print("Enter new password: ");
		String newPassword = scanner.nextLine();
		if (!isValidPassword1(newPassword)) {
			System.out.println("Invalid input. Password requirements not met.");
			return;
		}
		Account account = accounts.get(loggedInPhoneNumber);
		account.firstName = newFirstName;
		account.lastName = newLastName;
		account.password = newPassword;
		System.out.println("Account updated successfully!");
	}

	/**
	 * @author sibu
	 * Database interaction missing
	 * 
	 * Incorrect error messages
	 * On an invalid input, stay at the same menu item allowing the user to re-enter
	 * 
	 * Even if not interacting with the database, keep the messages in memory.
	 */	
// postMessage
	public static void postMessage() {
		if (loggedInPhoneNumber == null) {
			System.out.println("You need to be logged in to post a message.");
			return;
		}
		System.out.println("Posting a message");
		System.out.print("Enter recipient's phone number: ");
		String recipientPhoneNumber = scanner.nextLine();
		if (!isValidPhoneNumber(recipientPhoneNumber) || !accounts.containsKey(recipientPhoneNumber)) {
			System.out.println("Recipient does not exist");
			return;
		}
		System.out.print("Enter your message: ");
		String message = scanner.nextLine();
		if (!isValidMessage(message)) {
			System.out.println("Invalid input. Message should have at least one non-space character.");
			return;
		}
		System.out.println("Message sent to recipient successfully!");
	}

	public static boolean isValidMessage(String message) {
		return message.trim().length() > 0;
	}

	
	/**
	 * @author sibu
	 * An invalid implementation
	 */	
// replyMessage
	public static void replyToMessage() {
		if (loggedInPhoneNumber == null) {
			System.out.println("You need to be logged in to reply to a message.");
			return;
		}
		System.out.println("Replying to a message");
		System.out.print("Enter recipient's phone number: ");
		String recipientPhoneNumber = scanner.nextLine();
		if (!isValidPhoneNumber(recipientPhoneNumber) || !accounts.containsKey(recipientPhoneNumber)) {
			System.out.println("Recipient does not exist");
			return;
		}
		displayLastFiveMessages(recipientPhoneNumber);
		System.out.print("Select the number of the message to reply to: ");
		int selectedMessageNumber = scanner.nextInt();
		scanner.nextLine(); // Consume the newline character
		if (selectedMessageNumber < 1 || selectedMessageNumber > 5) {
			System.out.println("Invalid input");
			return;
		}
		System.out.print("Enter your reply message: ");
		String replyMessage = scanner.nextLine();
		if (!isValidMessage(replyMessage)) {
			System.out.println("Invalid input. Message should have at least one non-space character.");
			return;
		}
		System.out.println("Reply message sent successfully!");
	}

	private static void displayLastFiveMessages(String recipientPhoneNumber) {
		System.out.println(recipientPhoneNumber);
	}

	/**
	 * @author sibu
	 * An invalid implementation
	 */	
// forwardMessage
	public static void forwardMessage() {
		if (loggedInPhoneNumber == null) {
			System.out.println("You need to be logged in to forward a message.");
			return;
		}
		System.out.println("Forwarding a message");
		System.out.print("Enter sender's phone number: ");
		String senderPhoneNumber = scanner.nextLine();
		if (!isValidPhoneNumber(senderPhoneNumber) || !accounts.containsKey(senderPhoneNumber)) {
			System.out.println("Sender does not exist");
			return;
		}
		displayLastFiveMessages(senderPhoneNumber);
		System.out.print("Select the number of the message to forward: ");
		int selectedMessageNumber = scanner.nextInt();
		scanner.nextLine();
		if (selectedMessageNumber < 1 || selectedMessageNumber > 5) {
			System.out.println("Invalid input");
			return;
		}
		System.out.print("Enter recipient's phone number: ");
		String recipientPhoneNumber = scanner.nextLine();
		if (!isValidPhoneNumber(recipientPhoneNumber) || !accounts.containsKey(recipientPhoneNumber)) {
			System.out.println("Recipient does not exist");
			return;
		}
		if (senderPhoneNumber.equals(recipientPhoneNumber)) {
			System.out.println("Select a different recipient");
			return;
		}
		System.out.println("Message forwarded successfully!");
	}

//Logout
	public static void logout() {
		loggedInPhoneNumber = null;
		System.out.println("Logged out successfully!");
	}
}