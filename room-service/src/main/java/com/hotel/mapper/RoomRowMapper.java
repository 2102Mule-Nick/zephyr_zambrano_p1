package com.hotel.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hotel.pojo.Room;

@Component
public class RoomRowMapper implements RowMapper<Room> {
	
	private RoomExtractor roomExtractor;
	
	@Autowired
	public void setRoomExtractor(RoomExtractor roomExtractor) {
		this.roomExtractor = roomExtractor;
	}
	
	@Override
	public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
		return roomExtractor.extractData(rs);
	}
	
}