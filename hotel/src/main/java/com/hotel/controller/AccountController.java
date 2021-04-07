package com.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.dao.AccountDao;
import com.hotel.exception.AccountNotFound;
import com.hotel.exception.UsernameTaken;
import com.hotel.messaging.JmsMessageListener;
import com.hotel.messaging.JmsMessageSender;
import com.hotel.pojo.Account;

@Controller
public class AccountController {
	
	private AccountDao accountDao;
	
	private JmsTemplate jmsTemplate;
	
	private JmsMessageListener messageListener;
	
	private JmsMessageSender messageSender;
	
	private static final String accountUrl = "http://localhost:8080/account";
	
	@Autowired
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	@Autowired
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	@Autowired
	public void setJmsMessageListener(JmsMessageListener messageListener) {
		this.messageListener = messageListener;
	}
	
	@Autowired
	public void setJmsMessageSender(JmsMessageSender messageSender) {
		this.messageSender = messageSender;
	}
	
	@GetMapping("/account")
	@ResponseBody
	public List<Account> getAllAccounts() {
		return accountDao.getAllAccounts();
	}
	
	@GetMapping("/account/{username}")
	@ResponseBody
	public ResponseEntity<Account> getAccountByUsername(@PathVariable("username") String username) {
		try {
			return ResponseEntity.ok(accountDao.getAccountByUsername(username));
		}
		catch (AccountNotFound e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/account/username-taken/{username}")
	@ResponseBody
	public String checkIfUsernameTaken(@PathVariable("username") String username) {
		if (accountDao.checkIfUsernameTaken(username)) {
			return "Username taken";
		}
		else {
			return "Username available";
		}
	}
	
	@PostMapping("/account/create-account")
	@ResponseBody
	public String createAccount(@RequestBody Account account) {
		try {
			boolean success = accountDao.createAccount(account);
			
			if (success == true) {
				return "Account successfully created";
			}
			else {
				return "Unable to create account";
			}
		}
		catch (UsernameTaken e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Username taken; unable to create account";
		}
		
	}
	
	@PutMapping(path = "/account/update-account")
	@ResponseBody
	public String updateAccount(@RequestBody Account account) {
		
		
		if (accountDao.updateAccount(account)) {
			return "Account successfully updated";
		}
		else {
			return "Unable to update account";
		}
		
	}
	
	@DeleteMapping("/account/delete-account/{username}")
	@ResponseBody
	public String deleteAccount(@PathVariable("username") String username) {
		
		if (accountDao.deleteAccountByUsername(username)) {
			return "Account deleted";
		}
		else {
			return "Unable to delete account";	
		}
	}
	
	@DeleteMapping("/account/delete-account/{accoundId}")
	@ResponseBody
	public String deleteAccount(@PathVariable("accountId") int accountId) {
		
		if (accountDao.deleteAccountById(accountId)) {
			return "Account deleted";
		}
		else {
			return "Unable to delete account";	
		}
	}
	
	@DeleteMapping("/account/delete-account")
	@ResponseBody
	public String deleteAccount(@RequestBody Account account) {
		
		if (accountDao.deleteAccount(account)) {
			return "Account deleted";
		}
		else {
			return "Unable to delete account";	
		}
		
	}

}
