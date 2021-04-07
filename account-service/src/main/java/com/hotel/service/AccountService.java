package com.hotel.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.hotel.pojo.Account;

public class AccountService {
	
	private static final String accountUrl = "http://localhost:8080/hotel/account";

	public List<Account> getAllAccounts() {
		
		RestTemplate restTemplate = new RestTemplate();
		
		Account[] accounts = restTemplate.getForObject(accountUrl, Account[].class);
		
		return Arrays.asList(accounts);

	}

}
