package com.hotel.pojo;

public class Account {
	
	private int accountId;
	
	private String username;
	private String password;
	
	private String fullName;
	private String fullAddress;
	
	private String email;
	private String phoneNumber;
	
	public Account() {
		super();
	}
	
	public Account(String username, String password, String firstname, String middlename,
			String lastname, String street, String city, String state, String zipcode, 
			String email, String phoneNumber) {
		
		super();
		
		this.username = username;
		this.password = password;
		
		setFullName(firstname, middlename, lastname);
		
		setFullAddress(street, city, state, zipcode);
		
		this.email = email;
		this.phoneNumber = phoneNumber;
		
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setFullName(String firstname, String middlename, String lastname) {
		/**
		 * Sets the full name of an account user with the given first name, middle name, and last name.
		 * Saves the full name in the private String fullName.
		 * After doing this, it sets the separate private Strings firstname, middlename, and lastname
		 * with the given first name, middle name, and last name.
		 * 
		 * @param firstname		the account user's first name
		 * @param middlename	the account user's middle name
		 * @param lastname		the account user's last name
		 * 
		 */
		
		if (middlename.equals("")) { // user doesn't have a middle name
			this.fullName = firstname + " " + lastname;
		}
		else { // user has a middle name
			this.fullName = firstname + " " + middlename + " " + lastname;
		}
		
	}
	
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	
	public String getFullAddress() {
		return fullAddress;
	}
	
	public void setFullAddress(String street, String city, String state, String zipcode) {
		/**
		 * Sets the account user's full address.
		 * Saves the full address in the private String fullAddress, and then saves each
		 * piece of the address in their respective private String variables.
		 * 
		 * @param street	the given street
		 * @param city		the given city
		 * @param state		the given state
		 * @param zipcode	the given zip code
		 */
		
		this.fullAddress = street + " " + city + " " + state + " " + zipcode;
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
