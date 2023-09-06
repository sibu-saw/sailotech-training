package com.sailotech.assessment.second.nag;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	String messageText;
	long messageTimestamp;

	public Message(String messageText) {
		this.messageText = messageText;
		this.messageTimestamp = System.currentTimeMillis();
	}

}
