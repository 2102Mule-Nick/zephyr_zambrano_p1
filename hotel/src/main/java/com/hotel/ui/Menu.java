package com.hotel.ui;

import java.util.Scanner;

import com.hotel.dao.AccountDao;
import com.hotel.pojo.Account;

public interface Menu {
	
	public void displayOptions();
	
	public Menu advance();
	
	public Menu getPreviousMenu();
	
	public void setPreviousMenu(Menu previousMenu);
	
	public Menu getNextMenu();
	
	public void setNextMenu(Menu nextMenu);
	
	public Scanner getScanner();
	
	public void setScanner(Scanner scanner);
	
	public AccountDao getAccountDao();
	
	public void setAccountDao(AccountDao accountDao);

	public Account getAccount();
	
	public void setAccount(Account account);
	
}
