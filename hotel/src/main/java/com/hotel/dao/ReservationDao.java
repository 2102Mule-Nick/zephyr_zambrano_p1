package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hotel.dao.mapper.ReservationRowMapper;
import com.hotel.pojo.Account;
import com.hotel.pojo.Reservation;
import com.hotel.util.ConnectionFactoryPostgres;

@Repository
public class ReservationDao {
	
	private Logger log = Logger.getRootLogger();
	
	private JdbcTemplate jdbcTemplate;
	
	private ReservationRowMapper reservationRowMapper;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Autowired
	public void setReservationRowMapper(ReservationRowMapper reservationRowMapper) {
		this.reservationRowMapper = reservationRowMapper;
	}
	
	public ReservationDao() {
		super();
	}
	
	public Reservation getReservationById(int reservationId) {
		// TODO implement
		/**
		 * 
		 * 
		 */
		
		log.trace("ReservationDao.getReservationById");
		
		Reservation reservation = new Reservation();
		
		String sql = "select * from hotel.reservations where reservation_id = ?;";
		
		PreparedStatement preparedStatement;
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		log.info("Attempting to get a specific reservation by id from the database");
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, reservationId);
			ResultSet rs = preparedStatement.executeQuery();
			
			reservation.setAccountId(rs.getInt("account_id"));
			reservation.setReservationStartDate(rs.getDate("reservation_start_date"));
			reservation.setReservationEndDate(rs.getDate("reservation_start_date"));
			reservation.setCheckInTime(rs.getTime("check_in_time"));
			reservation.setCheckOutTime(rs.getTime("check_out_time"));
			reservation.setRoomType(rs.getString("room_type"));
			reservation.setRoomPrice(rs.getInt("room_price"));
			reservation.setNumberOfNights(rs.getInt("number_of_nights"));
			reservation.setReservationPrice(rs.getInt("reservation_price"));
			
			connection.close();
			log.info("Successfully got a specific reservation by id from the database");
		}
		catch (SQLException e) {
			log.error("Unable to get a specific reservation by id from the databases", e);
			e.printStackTrace();
		}
		
		return reservation;
		
	}
	
	public List<Reservation> getAllReservationsForASpecificAccount(String username) {
		// TODO implement
		/**
		 * 
		 * 
		 */
		
		log.trace("ReservationDao.getAllReservationsForASpecificAccount");
		
		String sql = "select * from hotel.reservations where user_name = ?;";
		
		List<Reservation> reservations = jdbcTemplate.query(sql, reservationRowMapper, username);
		
		return reservations;
		
	}
	
	public boolean createReservation(Reservation reservation) {
		// TODO change return type to return reservation?
		/**
		 * 
		 */
		
		log.trace("ReservationDao.createReservation");
		
		String sql = "insert into hotel.reservations "
				+ "(account_id, reservation_start_date, reservation_end_date, check_in_time, "
				+ "check_out_time, room_type, room_price, number_of_nights, reservation_price) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		
		PreparedStatement preparedStatement;
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		log.info("Attempting to create a new reservation using a prepared statement");
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1,  reservation.getAccountId());
			preparedStatement.setDate(2, reservation.getReservationStartDate());
			preparedStatement.setDate(3, reservation.getReservationEndDate());
			preparedStatement.setTime(4, reservation.getCheckInTime());
			preparedStatement.setTime(5, reservation.getCheckOutTime());
			preparedStatement.setString(6, reservation.getRoomType());
			preparedStatement.setInt(7, reservation.getRoomPrice());
			preparedStatement.setInt(8, reservation.getNumberOfNights());
			preparedStatement.setInt(9, reservation.getReservationPrice());
			preparedStatement.execute();
			
			connection.close();
			log.info("Successfully created a new reservation for the given account");
			return true;
		}
		catch (SQLException e) {
			log.error("Unable to connect to the database and create a new reservation for the given account", e);
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean updateReservation(Reservation reservation) {
		// TODO return the updated reservation instead?
		/**
		 * 
		 */
		
		log.trace("ReservationDao.updateReservation");
		
		String sql = "update hotel.reservations set "
				+ "reservation_start_date = ?, "
				+ "reservation_end_date = ?, "
				+ "check_in_time = ?, "
				+ "check_out_time = ?, "
				+ "room_type = ?, "
				+ "room_price = ?, "
				+ "number_of_nights = ?, "
				+ "reservation_price = ? "
				+ "where reservation_id = ?;";
		
		log.info("Attempting to update the reservation in the database using a prepared statement");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, reservation.getReservationStartDate());
			preparedStatement.setDate(2, reservation.getReservationEndDate());
			preparedStatement.setTime(3, reservation.getCheckInTime());
			preparedStatement.setTime(4, reservation.getCheckOutTime());
			preparedStatement.setString(5, reservation.getRoomType());
			preparedStatement.setInt(6, reservation.getRoomPrice());
			preparedStatement.setInt(7, reservation.getNumberOfNights());
			preparedStatement.setInt(8, reservation.getReservationPrice());
			preparedStatement.setInt(9, reservation.getReservationId());
			preparedStatement.execute();
			connection.close();
			
			log.info("Reservation successfully updated in the database using a prepared statement");
			return true;
		}
		catch (SQLException e) {
			log.error("Unable to update the reservation in the database using a prepared statement", e);
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean deleteReservation(Reservation reservation) {
		/**
		 * 
		 */
		
		log.trace("ReservationDao.deleteReservation");
		log.info("Attempting to delete a specific reservation for the current account");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		String sql = "delete from hotel.reservations where reservation_id = ?;";
		
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, reservation.getReservationId());
			preparedStatement.execute();
			
			connection.close();
			log.info("Successfully deleted a specific reservation for the given account");
			return true;
		}
		catch (SQLException e) {
			log.error("Unable to connect to database to delete a specific reservation for the given account");
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean deleteAllReservationsForASpecificAccount(Account account) {
		// if user deletes account but they still have reservations booked
		// or if the user just wants to cancel all their reservations
		/**
		 * 
		 */
		
		// TODO decide if i will be using accountId or username
		
		log.trace("ReservationDao.deleteAllReservations");
		log.info("Attempting to delete all reservations for the current account");
		
		String sql = "delete from accounts where username = ?;";
		
		if (jdbcTemplate.update(sql, account.getUsername()) == 0) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
}
