package com.hotel.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.hotel.config.JTAConfig;
import com.hotel.dao.AccountDao;
import com.hotel.dao.DateDao;
import com.hotel.dao.ReservationDao;
import com.hotel.dao.RoomDao;

@Component
public class JmsMessageListener implements MessageListener {
	
	AccountDao accountDao;
	
	ReservationDao reservationDao;
	
	RoomDao roomDao;
	
	DateDao dateDao;
	
	@Autowired
	public void setAccountDao(AccountDao inventoryDao) {
		this.accountDao = accountDao;
	}
	
	@Autowired
	public void setReservationDao(ReservationDao inventoryDao) {
		this.reservationDao = reservationDao;
	}
	
	@Autowired
	public void setRoomDao(RoomDao inventoryDao) {
		this.roomDao = roomDao;
	}
	
	@Autowired
	public void setDateDao(DateDao inventoryDao) {
		this.dateDao = dateDao;
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
		
		if (message instanceof ObjectMessage) {
			try {
				String msg = ((TextMessage) message).getText();
				System.out.println("================MESSAGE RECIEVED: " + msg + "===================");
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	@JmsListener(destination = JTAConfig.ACCOUNT_QUEUE)
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
		
		if (message instanceof ObjectMessage) {
			System.out.println("message sent");
		}
	}
	
	@JmsListener(destination = JTAConfig.RESERVATION_QUEUE)
	public void onReservationQueueMessage (Message message) {
		
		if (message instanceof TextMessage) {
			
			try {
				String text = ((TextMessage)message).getText();
				System.out.println("Reservation queue message: " + text);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	@JmsListener(destination = JTAConfig.ROOM_TOPIC)
	public void onRoomTopicMessage(Message message) {
		
		if (message instanceof ObjectMessage) {
			
			ObjectMessage om = (ObjectMessage)message;
			try {
				String text = ((TextMessage)message).getText();
				System.out.println("Room topic message: " + text);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	@JmsListener(destination = JTAConfig.DATE_TOPIC)
	public void onDateTopicMessage(Message message) {
		
		if (message instanceof ObjectMessage) {
			
			ObjectMessage om = (ObjectMessage)message;
			try {
				String text = ((TextMessage)message).getText();
				System.out.println("Date topic message: " + text);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
