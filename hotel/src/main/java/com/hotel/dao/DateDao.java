package com.hotel.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hotel.dao.mapper.DateRowMapper;
import com.hotel.pojo.HotelDate;

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
	
	public List<HotelDate> getDates() {
		/**
		 * Connects to the Postgres database to retrieve available dates to reserve at the hotel.
		 * Also retrieves the check-in and check-out dates as well as check-in and check-out times.
		 * 
		 * @return		list of hotel dates (includes check-in date, check-out date, check-in time, and check-out time)
		 * @see HotelDate
		 */
		
		log.trace("DateDao.getDates");
		
		String sql = "select * from hotel.dates;";
		
		List<HotelDate> hotelDates = jdbcTemplate.query(sql, dateRowMapper);
		
		return hotelDates;
		
	}
	
}
