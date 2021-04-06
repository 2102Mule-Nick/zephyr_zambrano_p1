package com.hotel.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.hotel.config.AppConfig;
import com.hotel.dao.AccountDao;
import com.hotel.exception.UsernameTaken;
import com.hotel.pojo.Account;

@Component
public class JmsMessageListener implements MessageListener {
	
	AccountDao accountDao;
	
	@Autowired
	public void setAccountDao(AccountDao inventoryDao) {
		this.accountDao = accountDao;
	}

	@Override
	public void onMessage(Message message) {
		
		if (message instanceof TextMessage) {
			
			try {
				String msg = ((TextMessage) message).getText();
				System.out.println("================MESSAGE RECIEVED: " + msg + "===================");
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	@JmsListener(destination = AppConfig.ACCOUNT_QUEUE)
	public void onAccountQueueMessage (Message message) {
		
		if (message instanceof TextMessage) {
			
			try {
				String text = ((TextMessage)message).getText();
				System.out.println("Account queue message: " + text);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		System.out.println("Handling Account Message...");
		
		if (message instanceof ObjectMessage) {
			
			ObjectMessage om = (ObjectMessage)message;
			
			try {
				Account account = (Account)om.getObject();
				
				System.out.println(account.toString());
				System.out.println();
				
				try {
					accountDao.createAccount(account);
				} catch (UsernameTaken e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}
