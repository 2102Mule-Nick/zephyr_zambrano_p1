package com.hotel.messaging;

import java.util.List;

import javax.jms.Queue;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.hotel.pojo.Account;

@Service
public class JmsMessageSender {

	private JmsTemplate jmsTemplate;
	
	private Queue accountQueue;
	
	private Queue reservationQueue;
	
	private Topic roomTopic;
	
	private Topic dateTopic;
	
	@Autowired
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	@Autowired
	@Qualifier("accountQueue")
	public void setAccountQueue(Queue accountQueue) {
		this.accountQueue = accountQueue;
	}
	
	@Autowired
	@Qualifier("reservationQueue")
	public void setReservationQueue(Queue reservationQueue) {
		this.reservationQueue = reservationQueue;
	}
	
	@Autowired
	@Qualifier("roomTopic")
	public void setRoomTopic(Topic roomTopic) {
		this.roomTopic = roomTopic;
	}
	
	@Autowired
	@Qualifier("dateTopic")
	public void setDateTopic(Topic dateTopic) {
		this.dateTopic = dateTopic;
	}
	
	public void sendToAccountQueue(String msg) {
		jmsTemplate.send(accountQueue, (s) -> s.createTextMessage(msg));
	}
	
	public void sendToAccountQueue(List<Account> accounts) {
		for (Account account : accounts) {
			jmsTemplate.send(accountQueue, (s) -> s.createObjectMessage(account));
		}
	}
	
	public void sendToAccountQueue(String username, String password, String fullName, String fullAddress, String email, String phoneNumber) {
		System.out.println("not implemented");
		/*Account account = new Account();
		account.setUsername(username);;
		account.setPassword(password);
		account.setFullName(fullName);
		account.setFullAddress(fullAddress);
		account.setEmail(email);
		account.setPhoneNumber(phoneNumber);
		jmsTemplate.send(accountQueue, (s) -> s.createObjectMessage((Serializable) account));
		*/
	}
	
	public void sendToReservationQueue(String msg) {
		jmsTemplate.send(reservationQueue, (s) -> s.createTextMessage(msg));
	}
	
	public void sendToRoomTopic(String msg) {
		jmsTemplate.send(roomTopic, (s) -> s.createTextMessage(msg));
	}
	
	public void sendToDateTopic(String msg) {
		jmsTemplate.send(dateTopic, (s) -> s.createTextMessage(msg));
	}
	
}
