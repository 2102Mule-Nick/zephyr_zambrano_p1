package com.hotel.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hotel.dao.mapper.RoomRowMapper;
import com.hotel.pojo.Room;

@Repository
public class RoomDao {
	
	private Logger log = Logger.getRootLogger();
	
	private JdbcTemplate jdbcTemplate;
	
	private RoomRowMapper roomRowMapper;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Autowired
	public void setRoomRowMapper(RoomRowMapper roomRowMapper) {
		this.roomRowMapper = roomRowMapper;
	}
	
	public RoomDao() {
		super();
	}
	
	public List<Room> getRoomTypesAndPrices() {
		/**
		 * Connects to the Postgres database to retrieve the available room types and their prices per night.
		 * 
		 * @return		list of rooms (includes room type and room price)
		 * @see Room
		 */
		
		log.trace("RoomDao.getRoomTypesAndPrices");
		
		String sql = "select * from hotel.rooms;";
		
		List<Room> rooms = jdbcTemplate.query(sql, roomRowMapper);
		
		return rooms;
		
	}
	
}
