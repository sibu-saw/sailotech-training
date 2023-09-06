package com.sailotech.assessment.second.harika;

import java.util.ArrayList;
import java.util.Scanner;

public class Creation_of_account {
	String phoneNumber;
	String firstName;
	String lastName;
	String password;

	Creation_of_account(String phoneNumber, String firstName, String lastName, String password) {
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

	Message(String senderPhoneNumber, String recipientPhoneNumber, String content) {
		this.senderPhoneNumber = senderPhoneNumber;
		this.recipientPhoneNumber = recipientPhoneNumber;
		this.content = content;
	}
}
