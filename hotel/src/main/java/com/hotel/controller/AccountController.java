package com.hotel.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.dao.AccountDao;
import com.hotel.pojo.Account;

@Controller
public class AccountController {
	
	private AccountDao accountDao;
	
	@Autowired
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	@GetMapping("/account")
	@ResponseBody
	public ArrayList<Account> getAllAccounts() {
		return accountDao.getAllAccounts();
	}

}
