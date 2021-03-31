package com.hotel.pojo;

import java.util.List;

public class Reservation {

	private int accountId;
	
	private int numberOfRooms; // number of rooms the user wants to book
	
	// now go one by one and ask the user what type of room to book based on the # they want
	
	private List<String> roomType; // list of all the type of rooms the user wants to book
	
	// private String roomType;
	
	private String startDate; // use date or datetime object instead?
	
	private String endDate; // use date or datetime object instead?
	
	private int reservationPrice; // based on the type and number of rooms booked
	
}
