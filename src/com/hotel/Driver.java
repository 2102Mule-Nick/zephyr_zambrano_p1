package com.hotel;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.hotel.dao.AccountDao;
import com.hotel.ui.LoginMenu;
import com.hotel.ui.MainMenu;
import com.hotel.ui.Menu;
import com.hotel.ui.SignupMenu;
import com.hotel.ui.WelcomeMenu;

public class Driver {

	public static void main(String[] args) {
		
		Logger log = Logger.getRootLogger();
		log.trace("project runs");
		
		Scanner scanner = new Scanner(System.in);
		
		AccountDao accountDao = new AccountDao();
		
		Menu mainMenu = new MainMenu();
		Menu loginMenu = new LoginMenu();
		Menu signupMenu = new SignupMenu();
		
		Menu welcomeMenu = new WelcomeMenu(loginMenu, signupMenu);
		
		mainMenu.setScanner(scanner);
		loginMenu.setScanner(scanner);
		signupMenu.setScanner(scanner);
		welcomeMenu.setScanner(scanner);
		
		loginMenu.setNextMenu(mainMenu);
		signupMenu.setNextMenu(mainMenu);
		
		loginMenu.setPreviousMenu(welcomeMenu);
		signupMenu.setPreviousMenu(welcomeMenu);
		mainMenu.setPreviousMenu(mainMenu);
		
		loginMenu.setAccountDao(accountDao);
		signupMenu.setAccountDao(accountDao);
		mainMenu.setAccountDao(accountDao);
		
		Menu nextMenu = welcomeMenu;
		
		do {
			nextMenu.displayOptions();
			nextMenu = nextMenu.advance();
		} while (nextMenu != null);

	}

}
