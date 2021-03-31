package com.hotel.ui;

import java.util.Scanner;

import com.hotel.dao.AccountDao;
import com.hotel.pojo.Account;

public class MainMenu implements Menu {
	
	private Scanner scanner;
	
	private Menu previousMenu;
	
	private Menu nextMenu;
	
	private AccountDao accountDao;
	
	private Account account;
	
	public MainMenu() {
		super();
	}

	@Override
	public void displayOptions() {
		System.out.println("Main Menu");
		System.out.println();
		
		System.out.println("Welcome " + account.getUsername() + "!");
		System.out.println();
		
		String selection = "";
		System.out.println();
		
		boolean quit = false;
		
		while (quit == false) {
			
			System.out.println();
			System.out.println("What would you like to do?");
			System.out.println("a: account menu");
			System.out.println("r: reservation menu");
			System.out.println("h: help");
			System.out.println("q: quit");
			System.out.println();

			System.out.print("Please type in your selection: ");
			selection = scanner.nextLine();
			System.out.println();
			
			switch(selection) {
				case "a": // account menu
					System.out.println("Account Menu");
					System.out.println();
					System.out.println("What would you like to do?");
					System.out.println("v: view account details");
					System.out.println("u: update account information");
					System.out.println("d: delete account");
					
					System.out.print("Please type in your selection: ");
					selection = scanner.nextLine();
					System.out.println();
					
					switch(selection) {
						case "v": // view account info
							accountDao.viewAccountDetails(account);
							break;
						case "u": // update account
							updateAccount(selection);
							break;
						case "d": // delete account
							quit = deleteAccount(selection);
							break;
						default:
							System.out.println("Invalid input; please try again.");
							System.out.println();
					}
					break;
				case "r": // reservation menu
					// TODO reservation logic
					System.out.println("not yet implemented");
					
					System.out.print("Your selection: ");
					selection = scanner.nextLine();
					System.out.println();
					
					switch(selection) {
						case "v": // view reservation information
							accountDao.viewReservations(account);
							break;
						case "u": // update existing reservation
							// TODO implement
							System.out.println("Not yet implemented");
							break;
						case "d": // delete reservation
							// TODO implement
							System.out.println("Not yet implemented");
							break;
						default:
							System.out.println("Invalid input; please try again.");
							System.out.println();
					}
					break;
				case "h": // help menu
					help();
					break;
				case "q": // quit menu
					quit = quit(selection);
					break;
				default: // invalid selection
					System.out.println("Invalid input; please try again.");
					System.out.println();
			}
			
		}
		
	}
	
	// TODO separate manage account section into it's own method
	
	public void help() {
		// TODO add help menu info once menu options are finalized
		System.out.println("Help Menu");
		System.out.println();
		System.out.println("menu options not yet implemented");
		System.out.println();
	}
	
	public void updateAccount(String selection) {
		// TODO implement and fix
		
		System.out.println("You would like to update your account information.");
		System.out.println();
		
		System.out.println("What would you like to update?");
		System.out.println("a: address");
		System.out.println("c: contact information (email or phone number)");
		System.out.println("n: name");
		System.out.println("p: password");
		System.out.println("u: username");
		System.out.println();
		
		System.out.print("Your selection: ");
		selection = scanner.nextLine();
		System.out.println();
		
		if (selection.equals("a")) {
			System.out.println("You have selected address");
			System.out.println();
			
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
			
			account.setFullAddress(street, city, state, zipcode);
			
		}
		else if (selection.equals("c")) {
			System.out.println("You have selected contact information");
			System.out.println();
			
			System.out.println("would you like to update your email or phone number? ('e' / 'p')");
			System.out.print("Selection: ");
			selection = scanner.nextLine();
			
			if (selection.equals("e") || selection.equals("E")) {
				String email = "";
				while (email.equals("")) {
					System.out.print("Email: ");
					email = scanner.nextLine();
					System.out.println();
				}
				
				account.setEmail(email);
				
			}
			else if (selection.equals("p")) {
				String phoneNumber = "";
				while (phoneNumber.equals("")) {
					System.out.print("Phone number: ");
					phoneNumber = scanner.nextLine();
					System.out.println();
				}
				account.setPhoneNumber(phoneNumber);
				
			}
			else {
				System.out.println("Invalid selection; please try again");
				System.out.println();
			}
			
		}
		else if (selection.equals("n")) {
			System.out.println("You have selected name");
			System.out.println();
			
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
			
			account.setFullName(firstname, middlename, lastname);
		}
		else if (selection.equals("p")) {
			System.out.println("You have selected password");
			System.out.println();
			
			String password = "";
			while (password.equals("")) {
				System.out.print("New password: ");
				password = scanner.nextLine();
				System.out.println();
			}
			
			account.setPassword(password);
		}
		else if (selection.equals("u")) {
			System.out.println("You have selected username");
			System.out.println();
			
			String newUsername = "";
			
			boolean usernameTaken = true;
			
			while (usernameTaken == true) {

				while (newUsername.equals("")) {
					System.out.print("New username: ");
					newUsername = scanner.nextLine();
					System.out.println();
					
				}
				
				usernameTaken = accountDao.getAccountByUsername(newUsername);
			}
			
			account.setUsername(newUsername);
			
		}
		else {
			System.out.println("Invalid selection; please try again");
			System.out.println();
		}
		
		accountDao.updateAccount(account);
		
		System.out.println("Account information has been successfully updated.");
		System.out.println();
	}
	
	public void updateUsername(String selection) {
		
	}
	
	public void updatePassword(String selection) {
		
	}
	
	public void updateName(String selection) {
		
	}
	
	public void updateAddress(String selection) {
		
	}
	
	public void updateEmail(String selection) {
		
	}
	
	public void updatePhone(String selection) {
		
	}
	
	public boolean deleteAccount(String selection) {
		// TODO don't let a user delete their account if they still have hotel reservations set
		// TODO OR make sure they know deleting account will cancel all existing reservations
		
		System.out.println("Are you sure you want to delete your account? Type 'DELETE ACCOUNT' to confirm.");
		System.out.println("Hit any key to cancel account deletion.");
		System.out.print("Your selection: ");
		selection = scanner.nextLine();
		System.out.println();
		
		switch(selection) {
			case "DELETE ACCOUNT":
				accountDao.deleteAccount(account);
				
				System.out.println("Account successfully deleted. Have a nice day!");
				System.out.println();
				
			default:
				System.out.println("Account deletion cancelled");
				System.out.println();
				return false;
		}
		
	}
	
	public boolean quit(String selection) {
		System.out.println("Are you sure you want to quit? (y / n)");
		System.out.print("Your selection: ");
		selection = scanner.nextLine();
		System.out.println();
		
		switch(selection) {
			case "y":
				System.out.println("Thank you and have a great day!");
				return true;
			case "n":
				return false;
			default:
				System.out.println("Invalid selection; please try again.");
				System.out.println();
				return false;
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
