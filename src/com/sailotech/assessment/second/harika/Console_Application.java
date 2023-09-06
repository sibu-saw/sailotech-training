package com.sailotech.assessment.second.harika;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Console_Application {
	static ArrayList<Creation_of_account> accounts = new ArrayList<>();
	static Creation_of_account currentAccount = null;
	static ArrayList<Message> messages = new ArrayList<>();
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		Console_Application application = new Console_Application();
		application.run();
	}

	void run() {
		while (true) {
			System.out.println("\nMenu:");
			System.out.println("1. Create an account");
			System.out.println("2. Log into an account");
			System.out.println("3. Edit an account");
			System.out.println("4. Post a message to a recipient");
			System.out.println("5. Reply to a message from a recipient");
			System.out.println("6. Forward a message from a recipient to another");
			System.out.println("7. Log out");
			System.out.println("8. Exit");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();
			sc.nextLine();
			switch (choice) {
			case 1:
				createAccount();
				break;
			case 2:
				logIn();
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
				logOut();
				break;
			case 8:
				System.out.println("**Exiting**");
				System.exit(0);
			default:
				System.out.println("Invalid choice. Please select a valid option.");
			}
		}
	}

	static void createAccount() { // creating an account
		System.out.println("Enter phone number (10 digits): ");
		String phoneNumber = sc.nextLine();
//valid phone number
		if (phoneNumber.length() != 10 || !phoneNumber.matches("\\d+")) {
			System.out.println("Invalid input. Phone number must contain exactly 10 digits.");
			return;
		}
		for (Creation_of_account account : accounts) {
			if (account.phoneNumber.equals(phoneNumber)) {
				System.out.println("Account already exists.");
				return;
			}
		}
		System.out.println("Enter first name: ");
		String firstName = sc.nextLine();
		if (!firstName.matches("[A-Za-z]{1,20}")) {
			System.out.println(
					"Invalid input. First name should contain only English alphabets with a maximum length of 20.");
			return;
		}
		System.out.println("Enter last name: ");
		String lastName = sc.nextLine();
		if (!lastName.matches("[A-Za-z]{1,20}")) {
			System.out.println(
					"Invalid input. Last name should contain only English alphabets with a maximum length of 20.");
			return;
		}
		System.out.println("Enter password: ");
		String password = sc.nextLine();
		if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%&*(){}\\[\\]])[A-Za-z\\d!@#$%&*(){}\\[\\]]{8,12}$")) {
			System.out.println(
					"Invalid input. Password should contain at least one alphabet, one numeric, one special character, and be 8 to 12 characters long.");
			return;
		}
		Creation_of_account account = new Creation_of_account(phoneNumber, firstName, lastName, password);
		accounts.add(account);
		System.out.println("*****Account created successfully!!!!*****");
	}

	static void logIn() { // log in into an account
		System.out.println("Enter phone number (10 digits): ");
		String phoneNumber = sc.nextLine();
		if (phoneNumber.length() != 10 || !phoneNumber.matches("\\d+")) {
			System.out.println("Invalid input. Phone number must contain exactly 10 digits.");
			return;
		}
		System.out.println("Enter password: ");
		String password = sc.nextLine();
		if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%&*(){}\\[\\]])[A-Za-z\\d!@#$%&*(){}\\[\\]]{8,12}$")) {
			System.out.println(
					"Invalid input. Password should contain at least one alphabet, one numeric, one special character, and be 8 to 12 characters long.");
			return;
		}
		boolean found = false;
		for (Creation_of_account account : accounts) {
			if (account.phoneNumber.equals(phoneNumber) && account.password.equals(password)) {
				currentAccount = account;
				System.out.println("***Logged in successfully!***");
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("Incorrect phone number or password.");
		}
	}

	static void editAccount() { // edit an account
		if (currentAccount == null) {
			System.out.println("You need to log in first.");
			return;
		}
		System.out.println("Enter new first name: ");
		String newFirstName = sc.nextLine();
		if (!newFirstName.matches("[A-Za-z]{1,20}")) {
			System.out.println(
					"Invalid input. First name should contain only English alphabets with a maximum length of 20.");
			return;
		}
		System.out.println("Enter new last name: ");
		String newLastName = sc.nextLine();
		if (!newLastName.matches("[A-Za-z]{1,20}")) {
			System.out.println(
					"Invalid input. Last name should contain only English alphabets with a maximum length of 20.");
			return;
		}
		System.out.println("Enter new password: ");
		String newPassword = sc.nextLine();
		if (!newPassword
				.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%&*(){}\\[\\]])[A-Za-z\\d!@#$%&*(){}\\[\\]]{8,12}$")) {
			System.out.println(
					"Invalid input. Password should contain at least one alphabet, one numeric, one special character, and be 8 to 12 characters long.");
			return;
		}
		currentAccount.firstName = newFirstName;
		currentAccount.lastName = newLastName;
		currentAccount.password = newPassword;
		System.out.println("***Account information updated!****");
	}

	static void postMessage() { // Post a message to a recipient
		if (currentAccount == null) {
			System.out.println("You need to log in first.");
			return;
		}
		System.out.println("Enter recipient's phone number (10 digits): ");
		String recipientPhoneNumber = sc.nextLine();
		if (recipientPhoneNumber.length() != 10 || !recipientPhoneNumber.matches("\\d+")) {
			System.out.println("Invalid input. Phone number must contain exactly 10 digits.");
			return;
		}
		Creation_of_account recipientAccount = null;
		for (Creation_of_account account : accounts) {
			if (account.phoneNumber.equals(recipientPhoneNumber)) {
				recipientAccount = account;
				break;
			}
		}
		if (recipientAccount == null) {
			System.out.println("Recipient does not exist.");
			return;
		}
		System.out.println("Enter message: ");
		String messageContent = sc.nextLine().trim();
		if (messageContent.isEmpty()) {
			System.out.println("Invalid input. Message should have at least one non-space character.");
			return;
		}
		Message message = new Message(currentAccount.phoneNumber, recipientPhoneNumber, messageContent);
		messages.add(message);
		System.out.println("****Message sent successfully!****");
	}

	void replyToMessage() { // Reply to a message from a recipient
		if (currentAccount == null) {
			System.out.println("You need to log in first.");
			return;
		}
		System.out.println("Enter recipient's phone number (10 digits): ");
		String recipientPhoneNumber = sc.nextLine();
		if (recipientPhoneNumber.length() != 10 || !recipientPhoneNumber.matches("\\d+")) {
			System.out.println("Invalid input. Phone number must contain exactly 10 digits.");
			return;
		}
		Creation_of_account recipientAccount = null;
		for (Creation_of_account account : accounts) {
			if (account.phoneNumber.equals(recipientPhoneNumber)) {
				recipientAccount = account;
				break;
			}
		}
		if (recipientAccount == null) {
			System.out.println("Recipient does not exist.");
			return;
		}
		List<Message> recipientMessages = new ArrayList<>();
		for (Message message : messages) {
			if (message.recipientPhoneNumber.equals(currentAccount.phoneNumber)
					&& message.senderPhoneNumber.equals(recipientPhoneNumber)) {
				recipientMessages.add(message);
			}
		}
		if (recipientMessages.isEmpty()) {
			System.out.println("No messages from this recipient.");
			return;
		}
		System.out.println("Last 5 messages from the recipient:");
		for (int i = Math.max(recipientMessages.size() - 5, 0); i < recipientMessages.size(); i++) {
			System.out.println((i + 1) + ". " + recipientMessages.get(i).content);
		}
		System.out.println("Select the number of the message to reply to: ");
		int selectedMessageIndex = sc.nextInt();
		sc.nextLine();
		if (selectedMessageIndex < 1 || selectedMessageIndex > recipientMessages.size()) {
			System.out.println("Invalid input. Select a valid message number.");
			return;
		}
		System.out.println("Enter your reply: ");
		String replyMessage = sc.nextLine().trim();
		if (replyMessage.isEmpty()) {
			System.out.println("Invalid input. Reply message should have at least one non-space character.");
			return;
		}
		Message reply = new Message(currentAccount.phoneNumber, recipientPhoneNumber, replyMessage);
		messages.add(reply);
		System.out.println("***Reply sent successfully!***");
	}

	static void forwardMessage() { // Forward a message from a recipient to another
		if (currentAccount == null) {
			System.out.println("You need to log in first.");
			return;
		}
		System.out.println("Enter sender's phone number (10 digits): ");
		String senderPhoneNumber = sc.nextLine();
		if (senderPhoneNumber.length() != 10 || !senderPhoneNumber.matches("\\d+")) {
			System.out.println("Invalid input. Phone number must contain exactly 10 digits.");
			return;
		}
		Creation_of_account senderAccount = null;
		for (Creation_of_account account : accounts) {
			if (account.phoneNumber.equals(senderPhoneNumber)) {
				senderAccount = account;
				break;
			}
		}
		if (senderAccount == null) {
			System.out.println("Sender does not exist.");
			return;
		}
		List<Message> senderMessages = new ArrayList<>();
		for (Message message : messages) {
			if (message.recipientPhoneNumber.equals(currentAccount.phoneNumber)
					&& message.senderPhoneNumber.equals(senderPhoneNumber)) {
				senderMessages.add(message);
			}
		}
		if (senderMessages.isEmpty()) {
			System.out.println("No messages from this sender.");
			return;
		}
		System.out.println("Last 5 messages from the sender:");
		int index = 1;
		for (int i = senderMessages.size() - 1; i >= Math.max(senderMessages.size() - 5, 0); i--) {
			System.out.println(index + ". " + senderMessages.get(i).content);
			index++;
		}
		System.out.println("Select the number of the message to forward: ");
		int selectedMessageIndex = sc.nextInt();
		sc.nextLine(); // Consume newline
		if (selectedMessageIndex < 1 || selectedMessageIndex > senderMessages.size()) {
			System.out.println("Invalid input. Select a valid message number.");
			return;
		}
		System.out.println("Enter recipient's phone number (10 digits): ");
		String recipientPhoneNumber = sc.nextLine();
		if (recipientPhoneNumber.length() != 10 || !recipientPhoneNumber.matches("\\d+")) {
			System.out.println("Invalid input. Phone number must contain exactly 10 digits.");
			return;
		}
		Creation_of_account recipientAccount = null;
		for (Creation_of_account account : accounts) {
			if (account.phoneNumber.equals(recipientPhoneNumber)) {
				recipientAccount = account;
				break;
			}
		}
		if (recipientAccount == null) {
			System.out.println("Recipient does not exist.");
			return;
		}
		if (recipientPhoneNumber.equals(senderPhoneNumber)) {
			System.out.println("Select a different recipient.");
			return;
		}
		Message selectedMessage = senderMessages.get(selectedMessageIndex - 1);
		Message forwardMessage = new Message(currentAccount.phoneNumber, recipientPhoneNumber, selectedMessage.content);
		messages.add(forwardMessage);
		System.out.println("Message forwarded successfully!");
	}

	static void logOut() { // logout
		currentAccount = null;
		System.out.println("***Logged out successfully.***");
	}
}
