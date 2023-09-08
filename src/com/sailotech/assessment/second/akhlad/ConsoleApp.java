package com.sailotech.assessment.second.akhlad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleApp {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		try {

			String url = "jdbc:postgresql://localhost:5432/stchat";
			String user = "postgres";
			String password = "root";
			Connection connection = DriverManager.getConnection(url, user, password);

			boolean loggedIn = false;
			String loggedInPhoneNumber = "";
			String createAccount = "";
			String forwardMessage = "";
			String postMessage = "";
			String editAccount = "";
			String exportMessages = "";
			String logInAccount = "";

			while (true) {
				System.out.println("Menu:");
				System.out.println("1. Create an account");
				System.out.println("2. Log into an account");
				System.out.println("3. Edit an account");
				System.out.println("4. Post a message to a recipient");
				// System.out.println("5. Reply to a message from a recipient");
				System.out.println("6. Forward a message from a recipient to another");
				System.out.println("7. Export messages from a recipient");
				System.out.println("8. Log out");
				System.out.print("Enter your choice: ");

				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume newline character

				switch (choice) {
				case 1:
					createAccount = createAccount(connection, scanner);
					if (createAccount == "success") {
						System.out.println("record inserted successfully");
					} else {
						System.out.println(createAccount);
					}
					break;
				case 2:
					logInAccount = logInAccount(connection, scanner);
					if (logInAccount == "success") {
						System.out.println("logged in successfully");
						loggedIn = true;

					} else {
						System.out.println(logInAccount);
						loggedIn = false;
					}

					break;
				case 3:
					if (loggedIn) {
						editAccount = editAccount(connection, loggedInPhoneNumber, scanner);
						System.out.println(editAccount);
					} else {
						System.out.println("Please log in first.");
					}
					break;
				case 4:
					if (loggedIn) {
						postMessage = postMessage(connection, loggedInPhoneNumber, scanner);
						System.out.println(postMessage);
					} else {
						System.out.println("Please log in first.");
					}
					break;

				case 6:
					if (loggedIn) {
						forwardMessage = forwardMessage(connection, loggedInPhoneNumber, scanner);
						System.out.println(forwardMessage);
					} else {
						System.out.println("Please log in first.");
					}
					break;
				case 7:
					if (loggedIn) {
						exportMessages = exportMessages(connection, loggedInPhoneNumber);
						System.out.println(exportMessages);
					} else {
						System.out.println("Please log in first.");
					}
					break;
				case 8:
					loggedIn = false;
					loggedInPhoneNumber = "";
					System.out.println("Logged out successfully.");
					break;
				default:
					System.out.println("Invalid choice");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author sibu
	 * 
	 * On an invalid input, stay at the same menu item allowing the user to re-enter
	 * Incorrect validation message
	 * 
	 * Validations should have been at a central place
	 * Why are we moving ahead when there is a validation error
	 */
	// Method to create an account
	private static String createAccount(Connection connection, Scanner scanner) throws SQLException {
		String message = null;
		System.out.print("Enter phone number: ");
		String phoneNumber = scanner.nextLine();
		if (!phoneNumber.matches("\\d{10}")) {
			message = "Invalid input";

		}

		// Check if the account already exists
		PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM account WHERE phone_number = ?");
		checkStatement.setString(1, phoneNumber);
		ResultSet checkResult = checkStatement.executeQuery();
		if (checkResult.next()) {

			message = "Account already exists";
		}

		System.out.print("Enter first name: ");
		String firstName = scanner.nextLine();
		if (!firstName.matches("[a-zA-Z]{1,20}")) {

			message = "Invalid first name";
		}

		System.out.print("Enter last name: ");
		String lastName = scanner.nextLine();
		if (!lastName.matches("[a-zA-Z]{1,20}")) {
			System.out.println("");
			message = "Invalid lastName";
		}

		System.out.print("Enter password: ");
		String passwordInput = scanner.nextLine();
		if (!passwordInput
				.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&*(){}\\[\\]])[A-Za-z\\d@#$%^&*(){}\\[\\]]{8,12}$")) {

			message = "Invalid input. Password should contain at least one alphabet, one numeric, one special character, and be 8-12 characters long.";
		}

		// Insert data into the "account" table
		PreparedStatement insertStatement = connection.prepareStatement(
				"INSERT INTO account(phone_number, first_name, last_name, password) VALUES (?, ?, ?, ?)");
		insertStatement.setString(1, phoneNumber);
		insertStatement.setString(2, firstName);
		insertStatement.setString(3, lastName);
		insertStatement.setString(4, passwordInput);
		insertStatement.executeUpdate();
		message = "success";
		return message;

	}

	/**
	 * @author sibu
	 * 
	 * On an invalid input, stay at the same menu item allowing the user to re-enter
	 * 
	 * Validations should have been at a central place
	 * Why are we moving ahead when there is a validation error
	 * Get the password at the same time
	 * Where is the loggedInPhoneNumber getting set?
	 */
	// Method to log into an account
	private static String logInAccount(Connection connection, Scanner scanner) throws SQLException {
		String message = null;
		System.out.print("Enter phone number: ");
		String phoneNumber = scanner.nextLine();
		if (!phoneNumber.matches("\\d{10}")) {
			message = "Invalid input";

		}

		PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM account WHERE phone_number = ?");
		checkStatement.setString(1, phoneNumber);
		ResultSet checkResult = checkStatement.executeQuery();
		if (!checkResult.next()) {
			message = "Account does not exist";

		}

		System.out.print("Enter password: ");
		String passwordInput = scanner.nextLine();

		if (!passwordMatches(connection, phoneNumber, passwordInput)) {

			message = "Incorrect phone number or password";
		}
		if (message != null) {
			return message;
		} else {
			message = "success";
			return message;
		}

	}

	// Method to check if the password matches
	private static boolean passwordMatches(Connection connection, String phoneNumber, String passwordInput)
			throws SQLException {
		PreparedStatement passwordCheckStatement = connection
				.prepareStatement("SELECT * FROM account WHERE phone_number = ? AND password = ?");
		passwordCheckStatement.setString(1, phoneNumber);
		passwordCheckStatement.setString(2, passwordInput);
		ResultSet passwordCheckResult = passwordCheckStatement.executeQuery();
		return passwordCheckResult.next();
	}

	/**
	 * @author sibu
	 * 
	 * Invalid implementation
	 */
	// Method to edit an account
	private static String editAccount(Connection connection, String loggedInPhoneNumber, Scanner scanner)
			throws SQLException {
		String message = null;
		System.out.print("Enter new first name: ");
		String newFirstName = scanner.nextLine();
		if (!newFirstName.matches("[a-zA-Z]{1,20}")) {
			message = "Invalid first name input";

		}

		System.out.print("Enter new last name: ");
		String newLastName = scanner.nextLine();
		if (!newLastName.matches("[a-zA-Z]{1,20}")) {
			message = "Invalid last name input";

		}

		System.out.print("Enter new password: ");
		String newPassword = scanner.nextLine();
		if (!newPassword
				.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&*(){}\\[\\]])[A-Za-z\\d@#$%^&*(){}\\[\\]]{8,12}$")) {
			message = "Invalid input. Password should contain at least one alphabet, one numeric, one special character, and be 8-12 characters long.";

		}

		PreparedStatement updateStatement = connection.prepareStatement(
				"UPDATE account SET first_name = ?, last_name = ?, password = ? WHERE phone_number = ?");
		updateStatement.setString(1, newFirstName);
		updateStatement.setString(2, newLastName);
		updateStatement.setString(3, newPassword);
		updateStatement.setString(4, loggedInPhoneNumber);
		updateStatement.executeUpdate();
		message = "Account updated successfully!";
		return message;
	}

	/**
	 * @author sibu
	 * 
	 * Invalid implementation
	 */
	// Method to post a message to a recipient
	private static String postMessage(Connection connection, String senderPhoneNumber, Scanner scanner)
			throws SQLException {
		String message = null;
		System.out.print("Enter recipient's phone number: ");
		String recipientPhoneNumber = scanner.nextLine();
		if (!recipientPhoneNumber.matches("\\d{10}")) {
			message = "Invalid input";
			return message;
		}

		// Check if the recipient exists
		PreparedStatement checkRecipientStatement = connection
				.prepareStatement("SELECT * FROM account WHERE phone_number = ?");
		checkRecipientStatement.setString(1, recipientPhoneNumber);
		ResultSet checkRecipientResult = checkRecipientStatement.executeQuery();
		if (!checkRecipientResult.next()) {
			message = "Recipient does not exist";
			return message;
		}

		System.out.print("Enter message: ");
		String message1 = scanner.nextLine().trim();
		if (message1.isEmpty()) {
			message = "Invalid input";
			return message;
		}
		System.out.print("Enter sender's phone number: ");
		senderPhoneNumber = scanner.nextLine();
		if (!senderPhoneNumber.matches("\\d{10}")) {
			message = "Invalid input";
			return message;
		}

		System.out.print("Enter parentMessageId : ");
		Integer parentMessageId = scanner.nextInt();

		// Insert data into the "message" table
		PreparedStatement insertMessageStatement = connection.prepareStatement(
				"INSERT INTO message(sender_phone_number, recipient_phone_number, content,parent_message_id) VALUES (?, ?, ?,?)");
		insertMessageStatement.setString(1, senderPhoneNumber);
		insertMessageStatement.setString(2, recipientPhoneNumber);
		insertMessageStatement.setString(3, message1);
		insertMessageStatement.setInt(4, parentMessageId);
		insertMessageStatement.executeUpdate();
		message = " Message sent successfully!";
		return message;

	}

	/**
	 * @author sibu
	 * 
	 * Invalid implementation
	 */
	// Method to forward a message
	private static String forwardMessage(Connection connection, String senderPhoneNumber, Scanner scanner)
			throws SQLException {
		String message = null;
		System.out.print("Enter recipient's phone number: ");
		String recipientPhoneNumber = scanner.nextLine();
		if (!recipientPhoneNumber.matches("\\d{10}")) {
			System.out.println("Invalid input");
			message = "Entered recipient's phone number is invalid";
			return message;
		}

		// Check if the recipient exists
		PreparedStatement checkRecipientStatement = connection
				.prepareStatement("SELECT * FROM account WHERE phone_number = ?");
		checkRecipientStatement.setString(1, recipientPhoneNumber);
		ResultSet checkRecipientResult = checkRecipientStatement.executeQuery();
		if (!checkRecipientResult.next()) {
			System.out.println("Recipient does not exist");
			message = "Recipient does not exist";
			return message;
		}

		System.out.print("Enter message: ");
		String message1 = scanner.nextLine().trim();
		if (message1.isEmpty()) {
			System.out.println("Invalid input");
			message = "Invalid message";
			return message;
		}

		// Insert data into the "message" table with a new recipient
		PreparedStatement insertMessageStatement = connection.prepareStatement(
				"INSERT INTO message(sender_phone_number, recipient_phone_number, content) VALUES (?, ?, ?)");
		insertMessageStatement.setString(1, senderPhoneNumber);
		insertMessageStatement.setString(2, recipientPhoneNumber);
		insertMessageStatement.setString(3, message);
		insertMessageStatement.executeUpdate();
		message = "Message forwarded successfully";
		return message;

	}

	/**
	 * @author sibu
	 * 
	 * Invalid implementation
	 */
	// Method to export messages from a recipient
	private static String exportMessages(Connection connection, String recipientPhoneNumber) throws SQLException {
		String message = null;
		PreparedStatement exportStatement = connection
				.prepareStatement("SELECT content FROM message WHERE recipient_phone_number = ?");
		exportStatement.setString(1, recipientPhoneNumber);
		ResultSet resultSet = exportStatement.executeQuery();

		System.out.println("Exported messages:");
		while (resultSet.next()) {
			message = resultSet.getString("content");

		}
		return message;
	}

	/**
	 * @author sibu
	 * 
	 * Invalid implementation
	 */
	// Method to log out
	private static void logOut(boolean isLoggedIn, Connection connection) {

		if (isLoggedIn) {

			System.out.println("Logged out successfully.");
		} else {
			System.out.println("No user is logged in.");
		}
	}

}
