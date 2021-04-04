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

import com.hotel.dao.mapper.RoomRowMapper;
import com.hotel.pojo.Room;
import com.hotel.util.ConnectionFactoryPostgres;

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
	
	public ArrayList<Room> getRoomTypesAndPrices() {
		/**
		 * Connects to the Postgres database to retrieve the available room types and their prices per night.
		 * 
		 * @return		list of rooms (includes room type and room price)
		 * @see Room
		 */
		
		log.trace("RoomDao.getRoomTypesAndPrices");
		
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		String sql = "select * from hotel.rooms;";
		
		PreparedStatement preparedStatement;
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		log.info("Attempting to retrieve room prices from the database");
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				Room room = new Room();
				room.setType(rs.getString("room_type"));
				room.setPrice(rs.getInt("room_price"));
				rooms.add(room);
			}
			
			connection.close();
			log.info("Successfully retrieved the hotel room types and prices from the database");
		}
		catch (SQLException e) {
			log.error("Unable to connect to the database to retrieve hotel room types and prices", e);
			e.printStackTrace();
		}
		
		return rooms;
		
	}
	
}
