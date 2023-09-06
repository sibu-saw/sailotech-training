package com.sailotech.assessment.second.noushad;

class Account {
	private String firstName;
	private String lastName;
	private String phonenumber;
	private String password;

	public Account(String firstName, String lastName, String phonenumber, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phonenumber = phonenumber;
		this.password = password;
	}

	public boolean checkPassword(String password, String phonenumber) {
		return this.password.equals(password) && this.phonenumber.equals(phonenumber);
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] getMessages() {
		return null;
	}
}

class Message {
	String senderphonenumber;
	String recipientPhonenumber;
	String messagecontent;

	public Message(String senderphonenumber, String recipientPhonenumber, String messagecontent) {
		this.senderphonenumber = senderphonenumber;
		this.recipientPhonenumber = recipientPhonenumber;
		this.messagecontent = messagecontent;
	}
}
