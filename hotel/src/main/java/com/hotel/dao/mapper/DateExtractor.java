package com.hotel.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.hotel.pojo.HotelDate;

@Component
public class DateExtractor implements ResultSetExtractor<HotelDate> {

	@Override
	public HotelDate extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		HotelDate hotelDate = new HotelDate();
		
		hotelDate.setReservationDate(rs.getDate("reservation_date"));
		hotelDate.setCheckInTime(rs.getTime("check_in_time"));
		hotelDate.setCheckOutTime(rs.getTime("check_out_time"));
		
		return hotelDate;
	}
	
}