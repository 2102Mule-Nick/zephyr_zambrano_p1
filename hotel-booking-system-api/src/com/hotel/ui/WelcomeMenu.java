package com.hotel.ui;

import java.util.Scanner;

import com.hotel.dao.AccountDao;
import com.hotel.pojo.Account;

public class WelcomeMenu implements Menu {
	
	private Scanner scanner;
	
	private Menu loginMenu;
	
	private Menu signupMenu;
	
	private Menu previousMenu;
	
	private Menu nextMenu;
	
	private AccountDao accountDao;
	
	private Account account;
	
	public WelcomeMenu() {
		super();
	}

	public WelcomeMenu(Menu loginMenu, Menu signupMenu) {
		super();
		this.loginMenu = loginMenu;
		this.signupMenu = signupMenu;
	}

	@Override
	public void displayOptions() {
		
		System.out.println("Welcome to hotel booking application!");
		System.out.println();
		
		System.out.println("Would you like to log in or sign up? ('l' or log in, 's' for sign up, 'q' to quit'): ");
		System.out.print("Your selection: ");
		String selection = scanner.nextLine();
		System.out.println();
		
		if (selection.equals("l") || selection.equals("L")) {
			nextMenu = loginMenu;
		}
		else if (selection.equals("s") || selection.equals("S")) {
			nextMenu = signupMenu;
		}
		else if (selection.equals("q") || selection.equals("Q")) {
			System.out.println("Exiting program. Have a nice day!");
			System.out.println();
			nextMenu = null;
		}
		else {
			System.out.println("Invalid selection; please try again.");
			System.out.println();
			nextMenu = this;
		}
		
	}

	@Override
	public Menu advance() {
		return nextMenu;
	}

	public Menu getLoginMenu() {
		return loginMenu;
	}

	public void setLoginMenu(Menu loginMenu) {
		this.loginMenu = loginMenu;
	}

	public Menu getSignupMenu() {
		return signupMenu;
	}

	public void setSignupMenu(Menu signupMenu) {
		this.signupMenu = signupMenu;
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
