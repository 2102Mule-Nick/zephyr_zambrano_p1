package com.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.dao.ReservationDao;

@Controller
public class ReservationController {

private ReservationDao reservationDao;
	
	@Autowired
	public void setReservationDao(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}
	
	@GetMapping("/reservation")
	@ResponseBody
	public String implement() {
		return "Reservation Controller not yet implemented!";
	}
	
}
