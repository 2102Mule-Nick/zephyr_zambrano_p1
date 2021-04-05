package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
	
	public boolean checkIfUsernameTaken(String username) {
		/**
		 * Connects to the Postgres database to check if the given username is already used by an existing account.
		 * 
		 * @param username		username that the user uses to log into their account		
		 * @return				true if the given username is already taken and false otherwise
		 * @see SignupMenu
		 */
		
		log.trace("AccountDao.getAccountByUsername");
		
		String sql = "select * from hotel.accounts where user_name = ?;";
		
		List<Account> accounts = jdbcTemplate.query(sql, accountRowMapper, username);
		
		if (accounts == null) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	public Account getAccountByUsername(String username) throws AccountNotFound {
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
	
	public List<Account> getAllAccounts() {
		
		log.trace("AccountDao.getAllAccounts");
		
		String sql = "select * from accounts";
		
		List<Account> accounts = jdbcTemplate.query(sql, accountRowMapper);
		
		return accounts;
		
	}
	
	public boolean createAccount(Account account) throws UsernameTaken {
		/**
		 * Creates a new account in the Postgres database.
		 * Uses a prepared statement to protect against SQL injection attacks.
		 * 
		 * @param account	the account to be created
		 * @see Account
		 */
		
		if (checkIfUsernameTaken(account.getUsername())) {
			throw new UsernameTaken();
		}
		
		String sql = "insert into accounts "
				+ "(user_name, pass_word, full_name, full_address, email, phone_number)"
				+ "values (?, ?, ?, ?, ?, ?);";
		
		if (jdbcTemplate.update(sql, account.getUsername(), account.getPassword(), 
				account.getFullName(), account.getFullAddress(), 
				account.getEmail(), account.getPhoneNumber()) == 0) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	public boolean updateAccountById(Account account) {
		log.trace("AccountDao.updateAccount");
		
		String sql = "update accounts set "
				+ "user_name = ?, "
				+ "pass_word = ?, "
				+ "full_name = ?, "
				+ "full_address = ?, "
				+ "email = ?, "
				+ "phone_number = ? "
				+ "where account_id = ?;";
		
		if (jdbcTemplate.update(sql, account.getUsername(), account.getPassword(), 
				account.getFullName(), account.getFullAddress(), 
				account.getEmail(), account.getPhoneNumber(), account.getUsername()) == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean updateAccountByUsername(Account account) {
		log.trace("AccountDao.updateAccount");
		
		String sql = "update accounts set "
				+ "user_name = ?, "
				+ "pass_word = ?, "
				+ "full_name = ?, "
				+ "full_address = ?, "
				+ "email = ?, "
				+ "phone_number = ? "
				+ "where user_name = ?;";
		
		if (jdbcTemplate.update(sql, account.getUsername(), account.getPassword(), 
				account.getFullName(), account.getFullAddress(), 
				account.getEmail(), account.getPhoneNumber(), account.getUsername()) == 0) {
			return false;
		}
		else {
			return true;
		}
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
		
		log.trace("AccountDao.updateAccount");
		
		String sql = "update accounts set "
				+ "user_name = ?, "
				+ "pass_word = ?, "
				+ "full_name = ?, "
				+ "full_address = ?, "
				+ "email = ?, "
				+ "phone_number = ? "
				+ "where user_name = ?;";
		
		if (jdbcTemplate.update(sql, account.getUsername(), account.getPassword(), 
				account.getFullName(), account.getFullAddress(), 
				account.getEmail(), account.getPhoneNumber(), account.getUsername()) == 0) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	public boolean deleteAccountByUsername(String username) {
		log.trace("AccountDao.deleteAccountByUsername");
		
		String sql = "delete from accounts where username = ?;";
		
		if (jdbcTemplate.update(sql, username) == 0) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	public boolean deleteAccountById(int accountId) {
		log.trace("AccountDao.deleteAccountById");
		
		String sql = "delete from accounts where account_id = ?;";
		
		if (jdbcTemplate.update(sql, accountId) == 0) {
			return false;
		}
		else {
			return true;
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
		
		String sql = "delete from accounts where username = ?;";
		
		if (jdbcTemplate.update(sql, account.getUsername()) == 0) {
			return false;
		}
		else {
			return true;
		}

	}
	
}

