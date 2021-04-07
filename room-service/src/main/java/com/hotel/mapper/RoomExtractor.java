package com.hotel.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.hotel.pojo.Room;

@Component
public class RoomExtractor implements ResultSetExtractor<Room> {

	// @Override
	public Room extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		Room room = new Room();
		
		room.setType(rs.getString("room_type"));
		room.setPrice(rs.getInt("room_price"));
		
		return room;
		
	}
	
}