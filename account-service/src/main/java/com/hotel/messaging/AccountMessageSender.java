package com.hotel.messaging;

import java.io.Serializable;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.hotel.dao.AccountDao;
import com.hotel.service.AccountService;

@Service
public class AccountMessageSender {
	
	private JmsTemplate jmsTemplate;
	
	private Queue accountQueue;
	
	private AccountDao accountDao;
	
	private AccountService accountService;
	
	@Autowired
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	@Autowired
	@Qualifier("destinationQueue")
	public void setAccountQueue(Queue accountQueue) {
		this.accountQueue = accountQueue;
	}
	
	@Autowired
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	public void sendToAccountQueue(String msg) {
		System.out.println("account queue works!");
		
		jmsTemplate.send(accountQueue, session -> session.createTextMessage(msg));
		
		// jmsTemplate.send(accountQueue, (s) -> s.createTextMessage(msg));
	}
	
	public void sendAllAccounts(String msg) {
		
		jmsTemplate.send(accountQueue, session -> session.createObjectMessage((Serializable) accountService.getAllAccounts()));
	}
	
}
