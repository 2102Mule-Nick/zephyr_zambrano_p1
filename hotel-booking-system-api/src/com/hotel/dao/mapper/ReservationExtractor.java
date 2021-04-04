package com.hotel.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.hotel.pojo.Reservation;

@Component
public class ReservationExtractor implements ResultSetExtractor<Reservation> {

	@Override
	public Reservation extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		Reservation reservation = new Reservation();
		
		reservation.setReservationId(rs.getInt("reservation_id"));
		reservation.setAccountId(rs.getInt("account_id"));
		reservation.setReservationStartDate(rs.getDate("reservation_start_date"));
		reservation.setReservationEndDate(rs.getDate("reservation_start_date"));
		reservation.setCheckInTime(rs.getTime("check_in_time"));
		reservation.setCheckOutTime(rs.getTime("check_out_time"));
		reservation.setRoomType(rs.getString("room_type"));
		reservation.setRoomPrice(rs.getInt("room_price"));
		reservation.setNumberOfNights(rs.getInt("number_of_nights"));
		reservation.setReservationPrice(rs.getInt("reservation_price"));
		
		return reservation;
		
	}
	
}
