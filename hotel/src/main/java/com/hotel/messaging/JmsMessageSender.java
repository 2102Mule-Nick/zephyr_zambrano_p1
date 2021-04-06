package com.hotel.messaging;

import javax.jms.Queue;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsMessageSender {

	private JmsTemplate jmsTemplate;
	
	private Queue exampleQueue;

	private Topic exampleTopic;
	
	private Topic roomTopic;
	
	@Autowired
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	@Autowired
	@Qualifier("destinationQueue")
	public void setExampleQueue(Queue exampleQueue) {
		this.exampleQueue = exampleQueue;
	}
	
	@Autowired
	public void setExampleTopic(Topic exampleTopic) {
		this.exampleTopic = exampleTopic;
	}
	
	public void sendToQueue(String msg) {
		jmsTemplate.send(exampleQueue, (s) -> s.createTextMessage(msg));
	}
	
	public void sendToTopic(String msg) {
		
		// jmsTemplate.send(queue, (s) -> s.createTextMessage(msg));

		jmsTemplate.send(exampleTopic, (s) -> s.createTextMessage(msg));
		
	}
	
	public void sendToRoomTopic(String msg) {
		
		// jmsTemplate.send(queue, (s) -> s.createTextMessage(msg));

		jmsTemplate.send(roomTopic, (s) -> s.createTextMessage(msg));
		
	}
	
}
