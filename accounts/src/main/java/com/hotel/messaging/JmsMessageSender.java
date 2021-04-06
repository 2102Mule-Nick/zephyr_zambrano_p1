package com.hotel.messaging;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsMessageSender {
	
	private JmsTemplate jmsTemplate;
	
	private Queue accountQueue;
	
	@Autowired
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	@Autowired
	@Qualifier("destinationQueue")
	public void setAccountQueue(Queue accountQueue) {
		this.accountQueue = accountQueue;
	}
	
	public void sendToAccountQueue(String msg) {
		jmsTemplate.send(accountQueue, (s) -> s.createTextMessage(msg));
	}
	
}
