package com.hotel.ws;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.hotel.dao.RoomDao;
import com.hotel.pojo.Room;

@WebService(endpointInterface = "com.hotel.ws.RoomService",
serviceName="roomService")
public class RoomServiceImpl implements RoomService {
	
	private RoomDao roomDao;
	
	@Autowired
	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}

	@Override
	public List<Room> getAllRooms() {
		List<Room> rooms = roomDao.getRoomTypesAndPrices();
		return rooms;
	}	
	
}
