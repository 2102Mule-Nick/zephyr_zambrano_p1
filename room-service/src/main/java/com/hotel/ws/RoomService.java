package com.hotel.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.hotel.pojo.Room;

@WebService
public interface RoomService {
	
	@WebMethod
	public List<Room> getAllRooms();
	
}
