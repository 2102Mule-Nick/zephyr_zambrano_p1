package com.hotel.ui;

import java.util.ArrayList;
import java.util.Scanner;

import com.hotel.dao.AccountDao;
import com.hotel.dao.DateDao;
import com.hotel.dao.ReservationDao;
import com.hotel.dao.RoomDao;
import com.hotel.pojo.Account;
import com.hotel.pojo.HotelDate;
import com.hotel.pojo.Room;

public class MainMenu implements Menu {
	
	private Scanner scanner;
	
	private Menu previousMenu;
	
	private Menu nextMenu;
	
	private AccountDao accountDao;
	
	private Account account;
	
	private RoomDao roomDao = new RoomDao();
	
	private DateDao dateDao = new DateDao();
	
	private ReservationDao reservationDao = new ReservationDao();
	
	// retreives the room types and prices from the database; retrieved immediately after logging in
	private ArrayList<Room> rooms = new ArrayList<Room>();
	
	// retreives the available dates to make a reservation, as well as check in and check out times
	// retrieved immediately after logging in
	private ArrayList<HotelDate> hotelDates = new ArrayList<HotelDate>();
	
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
		
		rooms = roomDao.getRoomTypesAndPrices();
		
		hotelDates = dateDao.getDates();
		
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
							System.out.println("Account Details");
							System.out.println();
							System.out.println("Username: " + account.getUsername());
							System.out.println("Password: " + account.getPassword());
							System.out.println();
							System.out.println("Name: " + account.getFullName());
							System.out.println("Address: " + account.getFullAddress());
							System.out.println("Email: " + account.getEmail());
							System.out.println("Phone Number: " + account.getPhoneNumber());
							System.out.println();
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
					System.out.println("Reservation Menu");
					System.out.println();
					System.out.println("What would you like to do?");
					System.out.println("c: check room types and prices");
					System.out.println("h: view hotel dates");
					System.out.println("v: view reservation information");
					System.out.println("u: update reeservation");
					System.out.println("d: delete reservation");
					
					System.out.print("Please type in your selection: ");
					selection = scanner.nextLine();
					System.out.println();
					
					switch(selection) {
						case "c": // check room types and prices
							System.out.println("Room Types and Prices");
							System.out.println();
							for (Room room : rooms) {
								System.out.println(room.getType() + " - $" + room.getPrice());
							}
							break;
						case "h": // hotel dates and check in and check out times
							System.out.println("Available Dates to Reserve Rooms");
							System.out.println();
							for (HotelDate hotelDate : hotelDates) {
								System.out.println("Date: " + hotelDate.getReservationDate());
								System.out.println("Check-In Time: " + hotelDate.getCheckInTime());
								System.out.println("Check-Out Time: " + hotelDate.getCheckOutTime());
								System.out.println();
							}
							break;
						case "v": // view reservation information
							reservationDao.viewReservations(account);
							reservationDao.getAllReservationsForASpecificAccount();
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
		System.out.println("e: email");
		System.out.println("c: phone number");
		System.out.println("n: name");
		System.out.println("p: password");
		System.out.println("u: username");
		System.out.println();
		
		System.out.print("Your selection: ");
		selection = scanner.nextLine();
		System.out.println();
		
		switch(selection) {
			case "a":
				updateAddress(selection);
			case "e":
				updateEmail(selection);
			case "c":
				updatePhoneNumber(selection);
			case "n":
				updateName(selection);
			case "u":
				updateUsername(selection);
			case "p":
				updatePassword(selection);
			default:
				System.out.println("Invalid selection; please try again");
				System.out.println();
		}
		
	}
	
	public void updateUsername(String selection) {
		
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
		
		accountDao.updateAccount(account);
		
		System.out.println("Account information has been successfully updated.");
		System.out.println();
		
	}
	
	public void updatePassword(String selection) {
		
		System.out.println("You have selected password");
		System.out.println();
		
		String password = "";
		while (password.equals("")) {
			System.out.print("New password: ");
			password = scanner.nextLine();
			System.out.println();
		}
		
		account.setPassword(password);
		
		accountDao.updateAccount(account);
		
		System.out.println("Account information has been successfully updated.");
		System.out.println();
		
	}
	
	public void updateName(String selection) {
		
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
		
		accountDao.updateAccount(account);
		
		System.out.println("Account information has been successfully updated.");
		System.out.println();
		
	}
	
	public void updateAddress(String selection) {
		
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
		
		accountDao.updateAccount(account);
		
		System.out.println("Account information has been successfully updated.");
		System.out.println();
		
	}
	
	public void updateEmail(String selection) {
		
		String email = "";
		while (email.equals("")) {
			System.out.print("Email: ");
			email = scanner.nextLine();
			System.out.println();
		}
		
		account.setEmail(email);
		
		accountDao.updateAccount(account);
		
		System.out.println("Account information has been successfully updated.");
		System.out.println();
		
	}
	
	public void updatePhoneNumber(String selection) {
		
		String phoneNumber = "";
		while (phoneNumber.equals("")) {
			System.out.print("Phone number: ");
			phoneNumber = scanner.nextLine();
			System.out.println();
		}
		account.setPhoneNumber(phoneNumber);
		
		accountDao.updateAccount(account);
		
		System.out.println("Account information has been successfully updated.");
		System.out.println();
		
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
