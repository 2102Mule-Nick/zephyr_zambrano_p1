package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hotel.dao.mapper.DateRowMapper;
import com.hotel.pojo.HotelDate;
import com.hotel.util.ConnectionFactoryPostgres;

@Repository
public class DateDao {
	
	private Logger log = Logger.getRootLogger();
	
	private JdbcTemplate jdbcTemplate;
	
	private DateRowMapper dateRowMapper;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Autowired
	public void setDateRowMapper(DateRowMapper dateRowMapper) {
		this.dateRowMapper = dateRowMapper;
	}
	
	public DateDao() {
		super();
	}
	
	public ArrayList<HotelDate> getDates() {
		/**
		 * Connects to the Postgres database to retrieve available dates to reserve at the hotel.
		 * Also retrieves the check-in and check-out dates as well as check-in and check-out times.
		 * 
		 * @return		list of hotel dates (includes check-in date, check-out date, check-in time, and check-out time)
		 * @see HotelDate
		 */
		
		log.trace("DateDao.getDates");
		
		ArrayList<HotelDate> hotelDates = new ArrayList<HotelDate>();
		
		String sql = "select * from hotel.dates;";
		
		PreparedStatement preparedStatement;
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		log.info("Attempting to retrieve dates, check in times, and check out times from the database");
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				HotelDate hotelDate = new HotelDate();
				hotelDate.setReservationDate(rs.getDate("reservation_date"));
				hotelDate.setCheckInTime(rs.getTime("check_in_time"));
				hotelDate.setCheckOutTime(rs.getTime("check_out_time"));
				hotelDates.add(hotelDate);
			}
			
			connection.close();
			log.info("Successfully retrieved dates, check in times, and check out times from the database");
		}
		catch (SQLException e) {
			log.error("Unable to retrieve dates, check in times, and check out times from the database", e);
			e.printStackTrace();
		}
		
		return hotelDates;
		
	}
	
}
