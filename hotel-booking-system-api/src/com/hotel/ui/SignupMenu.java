package com.hotel.ui;

import java.util.Scanner;

import com.hotel.dao.AccountDao;
import com.hotel.exception.AccountNotFound;
import com.hotel.exception.InvalidPassword;
import com.hotel.pojo.Account;

public class SignupMenu implements Menu {
	
	private Scanner scanner;
	
	private Menu previousMenu;
	
	private Menu nextMenu;
	
	private AccountDao accountDao;
	
	private Account account;
	
	public SignupMenu() {
		super();
	}

	@Override
	public void displayOptions() {

		System.out.println("You have selected signup");
		System.out.println();
		
		String username = "";
		
		boolean usernameTaken = true;
		
		while (usernameTaken == true) {
			
			username = "";
			
			while (username.equals("")) {
				System.out.print("Username: ");
				username = scanner.nextLine();
				System.out.println();
				
			}
			
			usernameTaken = accountDao.getAccountByUsername(username);
			
		}
		
		String password = "";
		while (password.equals("")) {
			System.out.print("Password: ");
			password = scanner.nextLine();
			System.out.println();
		}

		String firstname = "";
		while (firstname.equals("")) {
			System.out.print("First name: ");
			firstname = scanner.nextLine();
			System.out.println();
		}
		
		// middle name can be an empty string because not everyone has a middle name
		String middlename = "";
		System.out.print("Middle name (hit enter key if no middle name): ");
		middlename = scanner.nextLine();
		System.out.println();
		
		String lastname = "";
		while (lastname.equals("")) {
			System.out.print("Last name: ");
			lastname = scanner.nextLine();
			System.out.println();
		}
		
		String street = "";
		while (street.equals("")) {
			System.out.print("Street: ");
			street = scanner.nextLine();
			System.out.println();
		}
		
		String city = "";
		while (city.equals("")) {
			System.out.print("City: ");
			city = scanner.nextLine();
			System.out.println();
		}
		
		String state = "";
		while (state.equals("")) {
			System.out.print("State: ");
			state = scanner.nextLine();
			System.out.println();
		}
		
		String zipcode = "";
		while (zipcode.equals("")) {
			System.out.print("Zip code: ");
			zipcode = scanner.nextLine();
			System.out.println();
		}
		
		String email = "";
		while (email.equals("")) {
			System.out.print("Email: ");
			email = scanner.nextLine();
			System.out.println();
		}
		
		String phoneNumber = "";
		while (phoneNumber.equals("")) {
			System.out.print("Phone number: ");
			phoneNumber = scanner.nextLine();
			System.out.println();
		}
		
		Account newAccount = new Account(username, password, firstname, middlename, lastname,
				street, city, state, zipcode, email, phoneNumber);
		
		accountDao.createAccount(newAccount);
		
		try {
			newAccount = accountDao.getAccountByUsernameAndPassword(username, password);
			
			nextMenu.setAccount(newAccount);
			
		} catch (AccountNotFound | InvalidPassword e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Menu advance() {
		return nextMenu;
	}

	@Override
	public Menu getPreviousMenu() {
		return previousMenu;
	}

	@Override
	public void setPreviousMenu(Menu previousMenu) {
		this.previousMenu = previousMenu;
	}

	@Override
	public Menu getNextMenu() {
		return nextMenu;
	}

	@Override
	public void setNextMenu(Menu nextMenu) {
		this.nextMenu = nextMenu;
	}

	@Override
	public Scanner getScanner() {
		return scanner;
	}

	@Override
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

	@Override
	public AccountDao getAccountDao() {
		return accountDao;
	}

	@Override
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public Account getAccount() {
		return account;
	}

	@Override
	public void setAccount(Account account) {
		this.account = account;
	}
	
}
