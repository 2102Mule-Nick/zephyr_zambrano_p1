package com.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.dao.ReservationDao;
import com.hotel.pojo.Reservation;

@Controller
public class ReservationController {

private ReservationDao reservationDao;
	
	@Autowired
	public void setReservationDao(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}
	
	@GetMapping("/reservation")
	@ResponseBody
	public String reservation() {
		return "Reservation path";
	}
	
	@GetMapping("/reservation/{username}")
	@ResponseBody
	public List<Reservation> getAllReservations(@PathVariable("username") String username) {
		return reservationDao.getAllReservationsForASpecificAccount(username);
	}
	
}
