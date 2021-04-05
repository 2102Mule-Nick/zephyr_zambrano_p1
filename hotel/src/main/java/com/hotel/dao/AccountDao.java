package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hotel.dao.mapper.AccountRowMapper;
import com.hotel.exception.AccountNotFound;
import com.hotel.exception.InvalidPassword;
import com.hotel.exception.UsernameTaken;
import com.hotel.pojo.Account;
import com.hotel.util.ConnectionFactoryPostgres;

@Repository
public class AccountDao {
	
	private Logger log = Logger.getRootLogger();
	
	private JdbcTemplate jdbcTemplate;

	private AccountRowMapper accountRowMapper;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Autowired
	public void setAccountRowMapper(AccountRowMapper accountRowMapper) {
		this.accountRowMapper = accountRowMapper;
	}
	
	public AccountDao() {
		super();
	}
	
	public boolean getAccountByUsername(String username) {
		/**
		 * Connects to the Postgres database to check if the given username is already used by an existing account.
		 * 
		 * @param username		username that the user uses to log into their account		
		 * @return				true if the given username is already taken and false otherwise
		 * @see SignupMenu
		 */
		
		log.info("Checking to see if the username is taken");
		
		String sql = "select * from hotel.accounts where user_name = ?;";
		
		PreparedStatement preparedStatement;
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		try {
			
			log.info("Successfully connected to the database");

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			
			connection.close();
			
			if (rs.next()) {
				log.info("Username already taken");
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
	
	public Account getAccountByUsernamee(String username) throws AccountNotFound {
		/**
		 * Connects to the Postgres database to check if the given username is already used by an existing account.
		 * 
		 * @param username		username that the user uses to log into their account		
		 * @return				true if the given username is already taken and false otherwise
		 * @see SignupMenu
		 */
		
		log.info("Checking to see if the username is taken");
		
		String sql = "select * from hotel.accounts where user_name = ?;";
		
		PreparedStatement preparedStatement;
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		Account account = new Account();
		
		try {
			
			log.info("Successfully connected to the database");

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			connection.close();
			
			if (rs.next()) {
				account.setAccountId(rs.getInt("account_id"));
				account.setUsername(rs.getString("user_name"));
				account.setPassword(rs.getString("pass_word"));
				account.setFullName(rs.getString("full_name"));
				account.setFullAddress(rs.getString("full_address"));
				account.setEmail(rs.getString("email"));
				account.setPhoneNumber(rs.getString("phone_number"));
			}
			else {
				throw new AccountNotFound();
			}
			
		}
		catch (SQLException e) { // wrapper for any exception or error state the database would throw (not to be confused with wrapper classes)
			log.error("Unable to connect to the database", e);
			e.printStackTrace();
		}
		
		return account;
		
	}
	
	public Account getAccountByUsernameAndPassword(String username, String password) throws AccountNotFound, InvalidPassword {
		/**
		 * Connects to the Postgres database to attempt to retrieve an account with the give username and password.
		 * 
		 * @param username		the username input by the user
		 * @param password		the password input by the user
		 * @return Account		account from the database with matching username and password
		 * @exception AccountNotFound
		 * @exception InvalidPassword
		 * @see Account
		 * @see LoginMenu
		 */
		
		log.info("Attempting to retrieve an existing account from the database");
		
		Account account = null;
		
		String sql = "select * from hotel.accounts where user_name = ?;";
		
		PreparedStatement preparedStatement;
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		try {
			
			log.info("Successfully connected to the database");

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			
			connection.close();
			
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
	
	public ArrayList<Account> getAllAccounts() {
		
		log.trace("AccountDao.getAllAccounts");
		
		ArrayList<Account> accounts = new ArrayList<Account>();
		
		String sql = "select * from hotel.accounts;";
		
		PreparedStatement preparedStatement;
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		log.info("Attempting to get all all accounts from the database");
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				
				Account account = new Account();
				
				account.setAccountId(rs.getInt("account_id"));
				account.setUsername(rs.getString("user_name"));
				account.setPassword(rs.getString("pass_word"));
				account.setFullName(rs.getString("full_name"));
				account.setFullAddress(rs.getString("full_address"));
				account.setEmail(rs.getString("email"));
				account.setPhoneNumber(rs.getString("phone_number"));
				
				accounts.add(account);
				
			}
			
			connection.close();
			log.info("Successfully got all accounts from the database");
		}
		catch (SQLException e) {
			log.error("Unable to get all accounts from the database", e);
			e.printStackTrace();
		}
		
		return accounts;
	}
	
	public boolean createAccount(Account account) {
	// public void createAccount(Account account) {
		/**
		 * Creates a new account in the Postgres database.
		 * Uses a prepared statement to protect against SQL injection attacks.
		 * 
		 * @param account	the account to be created
		 * @see Account
		 */
		
		/*
		String sql = "insert into hotel.accounts "
				+ "(user_name, pass_word, full_name, full_address, email, phone_number)"
				+ "values (?, ?, ?, ?, ?, ?);";
		
		jdbcTemplate.update(sql, account.getUsername(), account.getPassword(), 
		account.getFullName(), account.getFullAddress(), 
		account.getEmail(), account.getPhoneNumber());
		*/
		
		// /*
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
		// */
		
	}
	
	public boolean updateAccount(Account account) {
		/**
		 * Connects to the Postgres database and updates the account.
		 * Uses a prepared statement to protect against SQL injection attacks.
		 * 
		 * @param account	the account to be updated
		 * @return			true if account was updated, false if it wasn't
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
	
	public boolean deleteAccountByUsername(String username) {
		log.trace("AccountDao.deleteAccountByUsername");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		String sql = "delete from hotel.accounts where username = ?;";
		
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
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
	
	public boolean deleteAccountById(int accountId) {
		log.trace("AccountDao.deleteAccountById");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		String sql = "delete from hotel.accounts where account_id = ?;";
		
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountId);
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
	
	public boolean deleteAccount(Account account) {
		/**
		 * Connects to the Postgres database to delete the account.
		 * Uses a prepared statement.
		 * 
		 * @param account	the account to be deleted
		 * @return			true if account was deleted, false if it wasn't
		 * @see Account
		 */
		
		// TODO don't let a user delete their account until they cancel all their reservations
		// TODO OR make sure user knows if they delete account it will delete ALL their reservations
		
		log.trace("deleteAccount method in AccountDaoPostgres class");
		
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
	
}

