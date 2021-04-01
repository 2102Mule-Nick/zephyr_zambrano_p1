package com.hotel.pojo;

import java.sql.Date;
import java.sql.Time;

public class HotelDate {
	
	private Date reservationDate;
	
	private Time checkInTime;
	
	private Time checkOutTime;
	
	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public Time getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Time checkInTime) {
		this.checkInTime = checkInTime;
	}

	public Time getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(Time checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public HotelDate(Date reservationDate, Time checkInTime, Time checkOutTime) {
		super();
		this.reservationDate = reservationDate;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
	}
	
	public HotelDate() {
		super();
	}
	
}
