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
			System.out.println("a: account info");
			System.out.println("b: balances");
			System.out.println("d: deposit");
			System.out.println("h: help");
			System.out.println("m: manage account");
			System.out.println("t: transfer money");
			System.out.println("q: quit");
			System.out.println("w: withdrawal");
			System.out.println();

			System.out.print("Please type in your selection: ");
			selection = scanner.nextLine();
			System.out.println();
			
			// TODO change this to a switch statement?
			
			if (selection.equals("a") || selection.equals("A")) {
				accountDao.viewAccountDetails(account);
			}
			else if (selection.equals("r") || selection.equals("R")) {
				accountDao.viewReservations(account);
			}
			else if(selection.equals("h") || selection.equals("H")) {
				help();
			}
			else if (selection.equals("m") || selection.equals("M")) {
				System.out.println("You would like to manage your account.");
				System.out.println();
				System.out.println("Would you like to update your account information or delete your account?");
				System.out.println("'u' for update / 'd' for delete");
				System.out.print("Your selection: ");
				selection = scanner.nextLine();
				System.out.println();
				
				if (selection.equals("u") || selection.equals("U")) {
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
					
					if (selection.equals("a") || selection.equals("A")) {
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
					else if (selection.equals("c") || selection.equals("C")) {
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
						else if (selection.equals("p") || selection.equals("P")) {
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
					else if (selection.equals("n") || selection.equals("N")) {
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
					else if (selection.equals("p") || selection.equals("P")) {
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
					else if (selection.equals("u") || selection.equals("U")) {
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
						
						accountDao.updateAccountUsername(account, account.getUsername(), newUsername);
						
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
				else if (selection.equals("d") || selection.equals("D")) {
					
					quit = deleteAccount(selection);
					
				}
				else {
					System.out.println("Invalid input; please try again.");
					System.out.println();
				}
			}
			else if(selection.equals("q") || selection.equals("Q")) {
				quit = quit(selection);
			}
			else {
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
	}
	
	public boolean deleteAccount(String selection) {
		// TODO don't let a user delete their account if they still have hotel reservations set
		
		System.out.println("Are you sure you want to delete your account? Type 'DELETE ACCOUNT' to confirm.");
		System.out.println("Hit the enter key to cancel account deletion.");
		System.out.print("Your selection: ");
		selection = scanner.nextLine();
		System.out.println();
		
		if (selection.equals("DELETE ACCOUNT")) {
			accountDao.deleteAccount(account);
			
			System.out.println("Account successfully deleted. Have a nice day!");
			System.out.println();
			
			return true;
		}
		else {
			System.out.println("Account deletion cancelled");
			System.out.println();
			return false;
		}
		
	}
	
	public boolean quit(String selection) {
		System.out.println("Are you sure you want to quit? (y / n)");
		System.out.print("Your selection: ");
		String confirmationSelection = scanner.nextLine();
		System.out.println();

		if (confirmationSelection.equals("y") || confirmationSelection.equals("Y")) {
			System.out.println("Thank you and have a great day!");
			return true;
		}
		else if (confirmationSelection.equals("n") || confirmationSelection.equals("N")) {
			return false;
		}
		else {
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
