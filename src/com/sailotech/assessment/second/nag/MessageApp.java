package com.sailotech.assessment.second.nag;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MessageApp {
	private static Map<String, Account> accounts = new HashMap<>();

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int choice;

		do {

			System.out.println("1. Create an account");
			System.out.println("2. Log into an account");
			System.out.println("3. Edit an account");
			System.out.println("4. Post a message to a recipient");
			System.out.println("5. Reply to a message from a recipient");
			System.out.println("6. Forward a message from a recipient to another");
			System.out.println("7. Export messages from a recipient");
			System.out.println("8. Log out");
			System.out.println("0. Exit");
			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				createAccount(scanner);
				break;
			case 2:
				loginAccount(scanner);
				break;
			case 3:
				editAccount(scanner);
				break;
			case 4:
				postMessage(scanner);
				break;
			case 5:
				replyToMessage(scanner);
				break;
			case 6:
				forwardMessage(scanner);
				break;
			case 7:
				exportMessages(scanner);
				break;
			case 8:
				logout();
				break;
			case 0:
				System.out.println("Exiting the application.");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		} while (choice != 0);
	}

	/**
	 * @author sibu
	 * Database interaction missing
	 * 
	 * On an invalid input, stay at the same menu item allowing the user to re-enter
	 * Validation for password missing
	 * 
	 * Validations should have been at a central place
	 */
	public static void createAccount(Scanner scanner) {
		System.out.print("Enter phonenumber (10 digits): ");
		String phoneNumber = scanner.nextLine();

		if (!Pattern.matches("\\d{10}", phoneNumber)) {
			System.out.println("Invalid input");
			return;
		}

		if (accounts.containsKey(phoneNumber)) {
			System.out.println("Account already exists");
			return;
		}

		System.out.print("Enter first name (up to 20  alphabets): ");
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

	}

	/**
	 * @author sibu
	 * Invalid implementation
	 */
	public static void loginAccount(Scanner scanner) {
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

	/**
	 * @author sibu
	 * No implementation
	 */
	public static void editAccount(Scanner scanner) {
	}

	/**
	 * @author sibu
	 * No implementation
	 */
	public static void postMessage(Scanner scanner) {

	}

	/**
	 * @author sibu
	 * No implementation
	 */
	public static void replyToMessage(Scanner scanner) {

	}

	/**
	 * @author sibu
	 * No implementation
	 */
	public static void forwardMessage(Scanner scanner) {

	}

	/**
	 * @author sibu
	 * No implementation
	 */
	public static void exportMessages(Scanner scanner) {

	}

	/**
	 * @author sibu
	 * Invalid implementation
	 */
	public static void logout() {

		System.out.println("Logged out successfully.");
	}
}
