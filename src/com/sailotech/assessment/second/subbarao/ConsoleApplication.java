package com.sailotech.assessment.second.subbarao;

import java.io.FileWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

class Account {
	String phoneNumber;
	String firstName;
	String lastName;
	String password;

	public Account(String phoneNumber, String firstName, String lastName, String password) {
		this.phoneNumber = phoneNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}
}

class Message {
	String senderPhoneNumber;
	String recipientPhoneNumber;
	String content;

	public Message(String senderPhoneNumber, String recipientPhoneNumber, String content) {
		this.senderPhoneNumber = senderPhoneNumber;
		this.recipientPhoneNumber = recipientPhoneNumber;
		this.content = content;
	}
}

public class ConsoleApplication {
	static Scanner scanner = new Scanner(System.in);
	static Map<String, Account> accounts = new HashMap<>();
	static Map<String, Queue<Message>> messages = new HashMap<>();
	static Account loggedInAccount = null;

	public static void main(String[] args) {
		while (true) {
			System.out.println("Menu:");
			System.out.println("1. Create an account");
			System.out.println("2. Log into an account");
			System.out.println("3. edit an account");
			System.out.println("4. post a message to a recipient");
			System.out.println("5. replyToMessage");
			System.out.println("6. forwardMessage");
			System.out.println("7. exportMessages");
			System.out.println("8. logout");
			System.out.println("9. exit");

			System.out.print("Enter your choice: ");
			String choice = scanner.nextLine();

			switch (choice) {
			case "1":
				createAccount();
				break;
			case "2":
				logIn();
				break;
			case "3":
				editAccount();
				break;
			case "4":
				postMessage();
				break;
			case "5":
				replyToMessage();
				break;
			case "6":
				forwardMessage();
				break;
			case "7":
				exportMessages();
				break;
			case "8":
				logOut();
				break;

			case "9":
				System.out.println("Exiting the application.");
				System.exit(0);

				break;
			case "\\u":
				System.out.println("Going back a menu level.");
				break;
			case "\\q":
				System.out.println("Exiting the application.");
				System.exit(0);
			default:
				System.out.println("Invalid choice.");
			}
		}
	}

	public static void createAccount() {
		System.out.print("Enter phone number (10 digits): ");
		String phoneNumber = scanner.nextLine();

		if (!Pattern.matches("\\d{10}", phoneNumber)) {
			System.out.println("Invalid input");
			return;
		}

		if (accounts.containsKey(phoneNumber)) {
			System.out.println("Account already exists");
			return;
		}

		System.out.print("Enter first name (up to 20 English alphabets): ");
		String firstName = scanner.nextLine();

		if (!Pattern.matches("[a-zA-Z]{1,20}", firstName)) {
			System.out.println("Invalid input");
			return;
		}

		System.out.print("Enter last name (up to 20 English alphabets): ");
		String lastName = scanner.nextLine();

		if (!Pattern.matches("[a-zA-Z]{1,20}", lastName)) {
			System.out.println("Invalid input");
			return;
		}

		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		if (!Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%&*(){}\\[\\]])[a-zA-Z\\d!@#$%&*(){}\\[\\]]{8,12}$",
				password)) {
			System.out.println(
					"Invalid input. Password should contain at least one alphabet, one numeric, one special character and be 8-12 characters long");
			return;
		}

		accounts.put(phoneNumber, new Account(phoneNumber, firstName, lastName, password));
		System.out.println("Account created successfully.");
	}

	public static void logIn() {
		System.out.print("Enter phone number (10 digits): ");
		String phoneNumber = scanner.nextLine();

		if (!Pattern.matches("\\d{10}", phoneNumber)) {
			System.out.println("Invalid input");
			return;
		}

		if (!accounts.containsKey(phoneNumber)) {
			System.out.println("Account does not exist");
			return;
		}

		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		if (!Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%&*(){}\\[\\]])[a-zA-Z\\d!@#$%&*(){}\\[\\]]{8,12}$",
				password)) {
			System.out.println(
					"Invalid input. Password should contain at least one alphabet, one numeric, one special character and be 8-12 characters long");
			return;
		}

		Account account = accounts.get(phoneNumber);
		if (account.password.equals(password)) {
			System.out.println("Logged in successfully.");
		} else {
			System.out.println("Incorrect phone number or password");
		}
	}

	static void editAccount() {
		if (loggedInAccount == null) {
			System.out.println("You need to be logged in to edit your account.");
			return;
		}

		System.out.print("Enter new first name (up to 20 English alphabets): ");
		String newFirstName = scanner.nextLine();

		if (!Pattern.matches("[a-zA-Z]{1,20}", newFirstName)) {
			System.out.println("Invalid input");
			return;
		}

		System.out.print("Enter new last name (up to 20 English alphabets): ");
		String newLastName = scanner.nextLine();

		if (!Pattern.matches("[a-zA-Z]{1,20}", newLastName)) {
			System.out.println("Invalid input");
			return;
		}

		System.out.print("Enter new password: ");
		String newPassword = scanner.nextLine();

		if (!Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%&*(){}\\[\\]])[a-zA-Z\\d!@#$%&*(){}\\[\\]]{8,12}$",
				newPassword)) {
			System.out.println(
					"Invalid input. Password should contain at least one alphabet, one numeric, one special character and be 8-12 characters long");
			return;
		}

		loggedInAccount.firstName = newFirstName;
		loggedInAccount.lastName = newLastName;
		loggedInAccount.password = newPassword;

		System.out.println("Account updated successfully.");

	}

	public static void postMessage() {
		if (loggedInAccount == null) {
			System.out.println("You need to be logged in to post a message.");
			return;
		}

		System.out.print("Enter recipient's phone number (10 digits): ");
		String recipientPhoneNumber = scanner.nextLine();

		if (!Pattern.matches("\\d{10}", recipientPhoneNumber)) {
			System.out.println("Invalid input");
			return;
		}

		if (!accounts.containsKey(recipientPhoneNumber)) {
			System.out.println("Recipient does not exist");
			return;
		}

		System.out.print("Enter message: ");
		String messageContent = scanner.nextLine().trim();

		if (messageContent.isEmpty()) {
			System.out.println("Invalid input");
			return;
		}

		messages.put(recipientPhoneNumber,
				(Queue<Message>) new Message(loggedInAccount.phoneNumber, recipientPhoneNumber, messageContent));
		System.out.println("Message sent successfully.");
	}

	public static void replyToMessage() {
		if (loggedInAccount == null) {
			System.out.println("You need to be logged in to reply to a message.");
			return;
		}

		System.out.print("Enter recipient's phone number (10 digits): ");
		String recipientPhoneNumber = scanner.nextLine();

		if (!Pattern.matches("\\d{10}", recipientPhoneNumber)) {
			System.out.println("Invalid input");
			return;
		}

		if (!accounts.containsKey(recipientPhoneNumber)) {
			System.out.println("Recipient does not exist");
			return;
		}

		Queue<Message> recipientMessages = messages.getOrDefault(recipientPhoneNumber, new LinkedList<>());
		if (recipientMessages.isEmpty()) {
			System.out.println("No messages to reply to.");
			return;
		}

		System.out.println("Last 5 messages from the recipient:");
		int count = 0;
		int selectedMessageIndex = -1;
		for (Message message : recipientMessages) {
			if (count >= 5) {
				break;
			}
			System.out.println(count + ". " + message.content);
			count++;
		}

		System.out.print("Enter the number of the message to reply to: ");
		selectedMessageIndex = Integer.parseInt(scanner.nextLine());

		if (selectedMessageIndex < 0 || selectedMessageIndex >= count) {
			System.out.println("Invalid input");
			return;
		}

		System.out.print("Enter your reply: ");
		String replyContent = scanner.nextLine().trim();

		if (replyContent.isEmpty()) {
			System.out.println("Invalid input");
			return;
		}

		recipientMessages = messages.getOrDefault(recipientPhoneNumber, new LinkedList<>());
		recipientMessages.offer(new Message(loggedInAccount.phoneNumber, recipientPhoneNumber, replyContent));
		messages.put(recipientPhoneNumber, recipientMessages);
		System.out.println("Reply sent successfully.");
	}

	public static void forwardMessage() {
		if (loggedInAccount == null) {
			System.out.println("You need to be logged in to forward a message.");
			return;
		}

		System.out.print("Enter sender's phone number (10 digits): ");
		String senderPhoneNumber = scanner.nextLine();

		if (!Pattern.matches("\\d{10}", senderPhoneNumber)) {
			System.out.println("Invalid input");
			return;
		}

		if (!accounts.containsKey(senderPhoneNumber)) {
			System.out.println("Sender does not exist");
			return;
		}

		Queue<Message> senderMessages = messages.getOrDefault(senderPhoneNumber, new LinkedList<>());
		if (senderMessages.isEmpty()) {
			System.out.println("No messages to forward.");
			return;
		}

		System.out.println("Last 5 messages from the sender:");
		int count = 0;
		int selectedMessageIndex = -1;
		for (Message message : senderMessages) {
			if (count >= 5) {
				break;
			}
			System.out.println(count + ". " + message.content);
			count++;
		}

		System.out.print("Enter the number of the message to forward: ");
		selectedMessageIndex = Integer.parseInt(scanner.nextLine());

		if (selectedMessageIndex < 0 || selectedMessageIndex >= count) {
			System.out.println("Invalid input");
			return;
		}

		Message selectedMessage = (Message) senderMessages.toArray()[selectedMessageIndex];

		System.out.print("Enter recipient's phone number (10 digits) to forward the message: ");
		String recipientPhoneNumber = scanner.nextLine();

		if (!Pattern.matches("\\d{10}", recipientPhoneNumber)) {
			System.out.println("Invalid input");
			return;
		}

		if (!accounts.containsKey(recipientPhoneNumber)) {
			System.out.println("Recipient does not exist");
			return;
		}

		if (senderPhoneNumber.equals(recipientPhoneNumber)) {
			System.out.println("Select a different recipient.");
			return;
		}

		Queue<Message> recipientMessages = messages.getOrDefault(recipientPhoneNumber, new LinkedList<>());
		recipientMessages
				.offer(new Message(loggedInAccount.phoneNumber, recipientPhoneNumber, selectedMessage.content));
		messages.put(recipientPhoneNumber, recipientMessages);
		System.out.println("Message forwarded successfully.");
	}

	public static void exportMessages() {
		if (loggedInAccount == null) {
			System.out.println("You need to be logged in to export messages.");
			return;
		}

		System.out.print("Enter recipient's phone number (10 digits): ");
		String recipientPhoneNumber = scanner.nextLine();

		if (!Pattern.matches("\\d{10}", recipientPhoneNumber)) {
			System.out.println("Invalid input");
			return;
		}

		if (!accounts.containsKey(recipientPhoneNumber)) {
			System.out.println("Recipient does not exist");
			return;
		}

		Queue<Message> recipientMessages = messages.getOrDefault(recipientPhoneNumber, new LinkedList<>());

		if (recipientMessages.isEmpty()) {
			System.out.println("No messages to export.");
			return;
		}

		String fileName = UUID.randomUUID().toString() + ".json";

		try (FileWriter fileWriter = new FileWriter(System.getProperty("user.home") + "/" + fileName)) {
			fileWriter.write("[");
			boolean firstMessage = true;
			for (Message message : recipientMessages) {
				if (!firstMessage) {
					fileWriter.write(",");
				}
				fileWriter.write("{\"messageText\":\"" + message.content + "\",\"messageTimestamp\":\""
						+ System.currentTimeMillis() + "\",\"From\":\"" + message.senderPhoneNumber + "\"}");
				firstMessage = false;
			}
			fileWriter.write("]");
			System.out.println("Messages exported to " + fileName + " successfully.");
		} catch (IOException e) {
			System.out.println("Error exporting messages: " + e.getMessage());
		}
	}

	public static void logOut() {
		loggedInAccount = null;
		System.out.println("Logged out successfully.");
	}

}
