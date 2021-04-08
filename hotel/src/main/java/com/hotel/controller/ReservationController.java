package com.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.dao.ReservationDao;
import com.hotel.exception.ReservationNotFound;
import com.hotel.pojo.Account;
import com.hotel.pojo.Reservation;

@Controller
public class ReservationController {

private ReservationDao reservationDao;
	
	@Autowired
	public void setReservationDao(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}
	
	/*@GetMapping("/reservation")
	@ResponseBody
	public String reservation() {
		return "Reservation path";
	}*/
	
	@GetMapping("/reservation")
	@ResponseBody
	public List<Reservation> getAllReservations() {
		return reservationDao.getAllReservations();
	}
	
	@GetMapping("/reservation/{accountId}/{reservationId}")
	@ResponseBody
	public ResponseEntity<Reservation> getReservationById(@PathVariable("reservationId") int reservationId) {
		try {
			return ResponseEntity.ok(reservationDao.getReservationById(reservationId));
		} catch (ReservationNotFound e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/reservation/{accountId}")
	@ResponseBody
	public List<Reservation> getAllReservationsForASpecificAccount(@PathVariable("accountId") int accountId) {
		return reservationDao.getAllReservationsForASpecificAccount(accountId);
	}
	
}
