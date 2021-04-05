package com.hotel.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.dao.RoomDao;
import com.hotel.exception.AccountNotFound;
import com.hotel.pojo.Room;

@Controller
public class RoomController {
	
	private RoomDao roomDao;
	
	@Autowired
	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}
	
	@GetMapping("/room")
	@ResponseBody
	public ResponseEntity<List<Room>> getRooms() {
		return ResponseEntity.ok(roomDao.getRoomTypesAndPrices());
	}

}
