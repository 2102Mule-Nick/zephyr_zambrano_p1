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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkInTime == null) ? 0 : checkInTime.hashCode());
		result = prime * result + ((checkOutTime == null) ? 0 : checkOutTime.hashCode());
		result = prime * result + ((reservationDate == null) ? 0 : reservationDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HotelDate other = (HotelDate) obj;
		if (checkInTime == null) {
			if (other.checkInTime != null)
				return false;
		} else if (!checkInTime.equals(other.checkInTime))
			return false;
		if (checkOutTime == null) {
			if (other.checkOutTime != null)
				return false;
		} else if (!checkOutTime.equals(other.checkOutTime))
			return false;
		if (reservationDate == null) {
			if (other.reservationDate != null)
				return false;
		} else if (!reservationDate.equals(other.reservationDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HotelDate [reservationDate=" + reservationDate + ", checkInTime=" + checkInTime + ", checkOutTime="
				+ checkOutTime + "]";
	}
	
}
