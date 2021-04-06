package com.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.dao.DateDao;
import com.hotel.pojo.HotelDate;

@Controller
public class DateController {
	
	private DateDao dateDao;
	
	@Autowired
	public void setDateDao(DateDao dateDao) {
		this.dateDao = dateDao;
	}
	
	@GetMapping("/date")
	@ResponseBody
	public ResponseEntity<List<HotelDate>> getDates() {
		return ResponseEntity.ok(dateDao.getDates());
	}
	
}
