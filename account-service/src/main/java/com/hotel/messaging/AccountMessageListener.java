package com.hotel.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.hotel.config.AccountAppConfig;
import com.hotel.dao.AccountDao;
import com.hotel.exception.UsernameTaken;
import com.hotel.pojo.Account;

@Component
public class AccountMessageListener implements MessageListener {

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
	
	@JmsListener(destination = AccountAppConfig.ACCOUNT_QUEUE)
	public void onAccountQueueMessage (Message message) {
		
		System.out.println("message received");
		
		if (message instanceof TextMessage) {
			
			try {
				String text = ((TextMessage)message).getText();
				System.out.println("Account queue message: " + text);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}
