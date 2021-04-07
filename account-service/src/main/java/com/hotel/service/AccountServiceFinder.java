package com.hotel.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hotel.pojo.Account;

@Service
public class AccountServiceFinder {
	
	private static final String accountUrl = "http://localhost:8080/hotel/account";
	
	public List<Account> getAllAccounts() {
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<List<Account>> accountList = restTemplate.exchange
				(accountUrl, HttpMethod.GET, null, new ParameterizedTypeReference <List<Account>> () {});
				
		return accountList.getBody();
		
		/*Account[] accounts = restTemplate.getForObject(accountUrl, Account[].class);
		
		System.out.println(accounts);
		
		return Arrays.asList(accounts);*/
		
	}
	
	public String getNumberOfAccounts() {
		
		
		RestTemplate restTemplate = new RestTemplate();
		
		String response = restTemplate.getForObject(accountUrl + "/number", String.class);
		
		return response;
		
	}
		

}
