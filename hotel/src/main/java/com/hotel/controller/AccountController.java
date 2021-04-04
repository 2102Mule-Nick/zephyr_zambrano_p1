package com.hotel.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.dao.AccountDao;
import com.hotel.exception.AccountNotFound;
import com.hotel.pojo.Account;

@Controller
public class AccountController {
	
	private AccountDao accountDao;
	
	@Autowired
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	@GetMapping("/account/{username}")
	@ResponseBody
	public ResponseEntity<Account> getAccountByUsername(@PathVariable("username") String username) {
		try {
			return ResponseEntity.ok(accountDao.getAccountByUsernamee(username));
		}
		catch (AccountNotFound e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/account")
	@ResponseBody
	public ArrayList<Account> getAllAccounts() {
		return accountDao.getAllAccounts();
	}

}
