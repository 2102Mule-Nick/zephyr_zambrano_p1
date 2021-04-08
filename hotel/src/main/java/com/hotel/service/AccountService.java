package com.hotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.dao.AccountDao;
import com.hotel.messaging.JmsMessageSender;
import com.hotel.pojo.Account;

import jdk.internal.org.jline.utils.Log;

@Service
public class AccountService {
	
	private JmsMessageSender messageSender;
	
	private AccountDao accountDao;
	
	@Autowired
	public void setjMSMessageSender(JmsMessageSender messageSender) {
		this.messageSender = messageSender;
	}
	
	@Autowired
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Transactional
	public List<Account> getAllAccounts() {
		
		List<Account> accounts = accountDao.getAllAccounts();
		
		messageSender.sendToAccountQueue(accounts.size() + " accounts accessed");
		
		messageSender.sendToAccountQueue(accounts);
		
		return accounts;
		
	}
	
	@Transactional
	public int getNumberOfAccounts() {
		
		List<Account> accounts = accountDao.getAllAccounts();
		
		int numberOfAccounts = accounts.size();
		
		messageSender.sendToAccountQueue("Number of accounts: " + numberOfAccounts);
		
		return numberOfAccounts;
		
	}
	
	@Transactional
	public void testTransaction() {
		System.out.println("transaction successful");
	}
	
}
