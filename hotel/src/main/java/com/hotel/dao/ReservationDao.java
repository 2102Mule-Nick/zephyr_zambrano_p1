package com.hotel.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hotel.dao.mapper.ReservationRowMapper;
import com.hotel.exception.ReservationNotFound;
import com.hotel.pojo.Account;
import com.hotel.pojo.Reservation;

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
	
	public Reservation getReservationById(int reservationId) throws ReservationNotFound {
		/**
		 * 
		 * 
		 */
		
		log.trace("ReservationDao.getReservationById");
		
		String sql = "select * from reservations where reservation_id = ?;";
		
		List<Reservation> reservations = jdbcTemplate.query(sql, reservationRowMapper, reservationId);

		if (reservations.size() == 0) {
			throw new ReservationNotFound();
		}
		else {
			return reservations.get(0);
		}
		
	}
	
	public List<Reservation> getAllReservationsForASpecificAccount(int accountId) {
		/**
		 * 
		 * 
		 */
		
		log.trace("ReservationDao.getAllReservationsForASpecificAccount");
		
		String sql = "select * from reservations where account_id = ?;";
		
		List<Reservation> reservations = jdbcTemplate.query(sql, reservationRowMapper, accountId);
		
		return reservations;
		
	}
	
	public List<Reservation> getAllReservations() {
		log.trace("ReservationDao.getAllReservations");
		
		String sql = "select * from reservations;";
		
		List<Reservation> reservations = jdbcTemplate.query(sql, reservationRowMapper);
		
		return reservations;
	}
	
	public boolean createReservation(Reservation reservation) {
		// TODO change return type to return reservation?
		/**
		 * 
		 */
		
		log.trace("ReservationDao.createReservation");
		
		String sql = "insert into reservations "
				+ "(account_id, reservation_start_date, reservation_end_date, check_in_time, "
				+ "check_out_time, room_type, room_price, number_of_nights, reservation_price) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		if (jdbcTemplate.update(sql, reservation.getAccountId(), reservation.getReservationStartDate(), 
				reservation.getReservationEndDate(), reservation.getCheckInTime(), 
				reservation.getCheckOutTime(), reservation.getRoomType(), reservation.getRoomPrice(), 
				reservation.getNumberOfNights(), reservation.getReservationPrice()) == 0) {
			return false;
		}
		else {
			return true;
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
		
		if (jdbcTemplate.update(sql, reservation.getReservationStartDate(), 
				reservation.getReservationEndDate(), reservation.getCheckInTime(), 
				reservation.getCheckOutTime(), reservation.getRoomType(), reservation.getRoomPrice(), 
				reservation.getNumberOfNights(), reservation.getReservationPrice(), 
				reservation.getReservationId())== 0) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	public boolean deleteReservation(Reservation reservation) {
		/**
		 * 
		 */
		
		log.trace("ReservationDao.deleteReservation");
		
		String sql = "delete from reservations where reservation_id = ?;";
		
		if (jdbcTemplate.update(sql, reservation.getReservationId()) == 0) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	public boolean deleteAllReservationsForASpecificAccount(int accountId) {
		// if user deletes account but they still have reservations booked
		// or if the user just wants to cancel all their reservations
		/**
		 * 
		 */
		
		log.trace("ReservationDao.deleteAllReservations");
		log.info("Attempting to delete all reservations for the current account");
		
		String sql = "delete from reservations where account_id = ?;";
		
		if (jdbcTemplate.update(sql, accountId) == 0) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
}
