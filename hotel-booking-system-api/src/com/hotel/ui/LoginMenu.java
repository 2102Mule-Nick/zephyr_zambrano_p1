package com.hotel.ui;

import java.util.Scanner;

import com.hotel.dao.AccountDao;
import com.hotel.exception.AccountNotFound;
import com.hotel.exception.InvalidPassword;
import com.hotel.pojo.Account;

public class LoginMenu implements Menu {
	
	private Scanner scanner;
	
	private Menu previousMenu;
	
	private Menu nextMenu;
	
	private AccountDao accountDao;
	
	private Account account;
	
	public LoginMenu() {
		super();
	}
	
	@Override
	public void displayOptions() {

		System.out.println("You have selected login");
		System.out.println();
		
		String username = "";
		while (username.equals("")) {
			System.out.print("Please enter your username: ");
			username = scanner.nextLine();
			System.out.println();
		}
		
		String password = "";
		while (password.equals("")) {
			System.out.print("Please enter your password: ");
			password = scanner.nextLine();
			System.out.println();
		}
		
		Account account = null;
		
		try {
			account = accountDao.getAccountByUsernameAndPassword(username, password);
		}
		catch (AccountNotFound e) {
			System.out.println("No account with that username exists; please try logging in again or create a new account.");
		}
		catch (InvalidPassword e) {
			System.out.println("Invalid password; please try again.");
		}
		finally {
			System.out.println();
		}
		
		if (account != null) {
			nextMenu.setAccount(account);
		}
		else {
			nextMenu = previousMenu;
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
