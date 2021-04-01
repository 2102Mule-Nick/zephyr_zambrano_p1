package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hotel.exception.AccountNotFound;
import com.hotel.exception.InvalidPassword;
import com.hotel.pojo.Account;
import com.hotel.pojo.HotelDate;
import com.hotel.pojo.Reservation;
import com.hotel.pojo.Room;
import com.hotel.util.ConnectionFactoryPostgres;

public class AccountDao {
	
	private Logger log = Logger.getRootLogger();
	
	/*private Connection connection;
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}*/
	
	public AccountDao() {
		super();
	}
	
	// TODO change return types for when I split these up into separate systems and send messages back and forth
	
	public boolean getAccountByUsername(String username) {
		/**
		 * Connects to the Postgres database to check if the given username is already used by an existing account.
		 * 
		 * @param username		
		 * @return true		returns true if the given username is already being used by an existing account
		 * @return false	returns false if the given username is not being used by any existing accounts
		 * @see SignupMenu
		 */
		
		log.info("Checking to see if the username is taken");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		try {
			
			log.info("Successfully connected to the database");
			
			String sql = "select * from hotel.accounts where user_name = '" + username + "';";
			
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(sql);
			
			connection.close();
			
			if (rs.next()) {
				log.warn("Username already taken");
				return true;
			}
			else {
				log.info("Username is available");
				return false;
			}
		
		}
		catch (SQLException e) { // wrapper for any exception or error state the database would throw (not to be confused with wrapper classes)
			log.error("Unable to connect to the database", e);
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public Account getAccountByUsernameAndPassword(String username, String password) throws AccountNotFound, InvalidPassword {
		/**
		 * Connects to the Postgres database to attempt to retrieve an account with the give username and password.
		 * 
		 * @param username		the username input by the user
		 * @param password		the password input by the user
		 * @return Account account from the database with matching username and password
		 * @exception AccountNotFound
		 * @exception InvalidPassword
		 * @see Account
		 * @see LoginMenu
		 */
		
		log.info("Attempting to retrieve an existing account from the database");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		Account account = null;
		
		try {
			
			log.info("Successfully connected to the database");
			
			String sql = "select * from hotel.accounts where user_name = '" + username + "';";
			
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				
				log.info("Account with matching username found in the database");
				
				if (rs.getString("pass_word").equals(password)) {
					log.info("The given password matches the account's password");
				}
				else {
					log.warn("Invalid password; the given password does not match this account's password");
					throw new InvalidPassword();
				}
				
				account = new Account();
				
				account.setAccountId(rs.getInt("account_id"));
				account.setUsername(rs.getString("user_name"));
				account.setPassword(rs.getString("pass_word"));
				account.setFullName(rs.getString("full_name"));
				account.setFullAddress(rs.getString("full_address"));
				account.setEmail(rs.getString("email"));
				account.setPhoneNumber(rs.getString("phone_number"));
			
				connection.close();
				
				return account;
			}
			
			log.warn("Account not found; no account with the given username exists");
			throw new AccountNotFound();
			
		}
		catch (SQLException e) { // wrapper for any exception or error state the database would throw (not to be confused with wrapper classes)
			log.error("Unable to connect to the database", e);
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean createAccount(Account account) {
		/**
		 * Creates a new account in the Postgres database.
		 * Uses a prepared statement to protect against SQL injection attacks.
		 * 
		 * @param account	the account to be created
		 * @see Account
		 */
		
		String sql = "insert into hotel.accounts "
				+ "(user_name, pass_word, full_name, full_address, email, phone_number)"
				+ "values (?, ?, ?, ?, ?, ?);";
		
		PreparedStatement preparedStatement;
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		log.info("Attempting to create a new account using a prepared statement");
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getUsername());
			preparedStatement.setString(2, account.getPassword());
			preparedStatement.setString(3, account.getFullName());
			preparedStatement.setString(4, account.getFullAddress());
			preparedStatement.setString(5, account.getEmail());
			preparedStatement.setString(6, account.getPhoneNumber());
			preparedStatement.execute();
			
			connection.close();
			log.info("Successfully created a new account using a prepared statement");
			return true;
		}
		catch (SQLException e) {
			log.error("Unable to connect to the database and create a new account using a prepared statement", e);
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean updateAccount(Account account) {
		/**
		 * Connects to the Postgres database and updates the account.
		 * Uses a prepared statement to protect against SQL injection attacks.
		 * 
		 * @param account	the account to be updated
		 * @see Account
		 */
		
		String sql = "update hotel.accounts set "
				+ "user_name = ?, "
				+ "pass_word = ?, "
				+ "full_name = ?, "
				+ "full_address = ?, "
				+ "email = ?, "
				+ "phone_number = ? "
				+ "where account_id = ?;";
		
		log.info("Attempting to update the account in the database using a prepared statement");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getUsername());
			preparedStatement.setString(2, account.getPassword());
			preparedStatement.setString(3, account.getFullName());
			preparedStatement.setString(4, account.getFullAddress());
			preparedStatement.setString(5, account.getEmail());
			preparedStatement.setString(6, account.getPhoneNumber());
			preparedStatement.setInt(7, account.getAccountId());
			System.out.println(account.getAccountId());
			preparedStatement.execute();
			
			connection.close();
			
			log.info("Account successfully updated in the database using a prepared statement");
			return true;
		}
		catch (SQLException e) {
			log.error("Unable to update the account in the database using a prepared statement", e);
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean deleteAccount(Account account) {
		/**
		 * Connects to the Postgres database to delete the account.
		 * Uses a prepared statement.
		 * 
		 * @param account	the account to be deleted
		 * @see Account
		 */
		
		// TODO don't let a user delete their account until they cancel all their reservations
		// TODO OR make sure user knows if they delete account it will delete ALL their reservations
		
		log.trace("deleteAccount method in AccountDaoPostgres class");
		log.info("Attempting to delete account");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		String sql = "delete from hotel.accounts where user_name = ?;";
		
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getUsername());
			preparedStatement.execute();
			
			connection.close();
			log.info("Successfully deleted the account in the database using a prepared statement");
			return true;
		}
		catch (SQLException e) {
			log.error("Unable to connect to database to delete account using a prepared statement");
			e.printStackTrace();
			return false;
		}

	}
	
	public void viewAccountDetails(Account account) {
		// TODO change this so it returns account details instead
		/**
		 * Prints the user's account details.
		 * 
		 * @param account	the account to print the details from
		 * @see Account
		 */
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
	}
	
	public void viewReservations(Account account) {
		// TODO make sure it returns reservation details instead of just printing them
		// TODO implement this method
		/**
		 * Allows the user to view their existing hotel reservations.
		 * 
		 * @param account	The account the hotel reservations were made from.
		 * @see Reservation
		 */
		System.out.println("Not yet implemented");
		System.out.println();
	}
	
	public ArrayList<Room> getRoomTypesAndPrices() {
		// TODO comment
		/**
		 * 
		 */
		
		log.trace("AccountDao.getRoomTypesAndPrices");
		
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		String sql = "select * from hotel.rooms;";
		
		PreparedStatement preparedStatement;
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		log.info("Attempting to retrieve room prices from the database");
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				Room room = new Room();
				room.setType(rs.getString("room_type"));
				room.setPrice(rs.getInt("room_price"));
				rooms.add(room);
			}
			
			connection.close();
			log.info("Successfully retrieved the hotel room types and prices from the database");
		}
		catch (SQLException e) {
			log.error("Unable to connect to the database to retrieve hotel room types and prices", e);
			e.printStackTrace();
		}
		
		return rooms;
		
	}
	
	public ArrayList<HotelDate> getDates() {
		// TODO comment; move to HotelDate dao
		/**
		 * 
		 */
		
		log.trace("AccountDao.getDates");
		
		ArrayList<HotelDate> hotelDates = new ArrayList<HotelDate>();
		
		String sql = "select * from hotel.dates;";
		
		PreparedStatement preparedStatement;
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		log.info("Attempting to retrieve dates, check in times, and check out times from the database");
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				HotelDate hotelDate = new HotelDate();
				hotelDate.setReservationDate(rs.getDate("reservation_date"));
				hotelDate.setCheckInTime(rs.getTime("check_in_time"));
				hotelDate.setCheckOutTime(rs.getTime("check_out_time"));
				hotelDates.add(hotelDate);
			}
			
			connection.close();
			log.info("Successfully retrieved dates, check in times, and check out times from the database");
		}
		catch (SQLException e) {
			log.error("Unable to retrieve dates, check in times, and check out times from the database", e);
			e.printStackTrace();
		}
		
		return hotelDates;
		
	}
	
	public boolean createReservation(Reservation reservation) {
		// TODO change return type to return reservation?
		/**
		 * 
		 */
		
		log.trace("AccountDao.createReservation");
		
		String sql = "insert into hotel.reservations "
				+ "(account_id, reservation_start_date, reservation_end_date, check_in_time, "
				+ "check_out_time, room_type, room_price, reservation_price) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?);";
		
		
		PreparedStatement preparedStatement;
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		log.info("Attempting to create a new account using a prepared statement");
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1,  reservation.getAccountId());
			preparedStatement.setDate(2, reservation.getReservationStartDate());
			preparedStatement.setDate(3, reservation.getReservationEndDate());
			preparedStatement.setTime(4, reservation.getCheckInTime());
			preparedStatement.setTime(5, reservation.getCheckOutTime());
			preparedStatement.setString(6, reservation.getRoomType());
			preparedStatement.setInt(7, reservation.getRoomPrice());
			preparedStatement.setInt(8, reservation.getReservationPrice());
			preparedStatement.execute();
			
			connection.close();
			log.info("Successfully created a new reservation for the given account");
			return true;
		}
		catch (SQLException e) {
			log.error("Unable to connect to the database and create a new reservation for the given account", e);
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean updateReservation(Reservation reservation) {
		// TODO return the updated reservation instead?
		/**
		 * 
		 */
		
		log.trace("AccountDao.updateReservation");
		
		String sql = "update hotel.reservations set "
				+ "reservation_start_date = ?, "
				+ "reservation_end_date = ?, "
				+ "check_in_time = ?, "
				+ "check_out_time = ?, "
				+ "room_type = ?, "
				+ "room_price = ?, "
				+ "reservation_price = ? "
				+ "where account_id = ?;";
		
		log.info("Attempting to update the account in the database using a prepared statement");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, reservation.getReservationStartDate());
			preparedStatement.setDate(2, reservation.getReservationEndDate());
			preparedStatement.setTime(3, reservation.getCheckInTime());
			preparedStatement.setTime(4, reservation.getCheckOutTime());
			preparedStatement.setString(5, reservation.getRoomType());
			preparedStatement.setInt(6, reservation.getRoomPrice());
			preparedStatement.setInt(7, reservation.getReservationPrice());
			preparedStatement.setInt(8, reservation.getAccountId());
			preparedStatement.execute();
			connection.close();
			
			log.info("Reservation successfully updated in the database using a prepared statement");
			return true;
		}
		catch (SQLException e) {
			log.error("Unable to update the reservation in the database using a prepared statement", e);
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean deleteReservation(Reservation reservation) {
		/**
		 * 
		 */
		
		log.trace("AccountDao.deleteReservation");
		log.info("Attempting to delete a specific reservation for the current account");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		String sql = "delete from hotel.reservations where reservation_id = ?;";
		
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, reservation.getReservationId());
			preparedStatement.execute();
			
			connection.close();
			log.info("Successfully deleted a specific reservation for the given account");
			return true;
		}
		catch (SQLException e) {
			log.error("Unable to connect to database to delete a specific reservation for the given account");
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean deleteAllReservations(Account account) {
		// if user deletes account but they still have reservations booked
		// or if the user just wants to cancel all their reservations
		/**
		 * 
		 */
		
		log.trace("AccountDao.deleteAllReservations");
		log.info("Attempting to delete all reservations for the current account");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		String sql = "delete from hotel.reservations where account_id = ?;";
		
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, account.getAccountId());
			preparedStatement.execute();
			
			connection.close();
			log.info("Successfully deleted all reservations for the given account");
			return true;
		}
		catch (SQLException e) {
			log.error("Unable to connect to database to delete all reservations for the given account");
			e.printStackTrace();
			return false;
		}
		
	}
	
}

