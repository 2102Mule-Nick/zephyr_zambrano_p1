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
	
	// public List<Account> getAllAccounts() {
	public Account getAccount() {
		
		RestTemplate restTemplate = new RestTemplate();
		
		/*ResponseEntity<List<Account>> accountList = restTemplate.exchange
				(accountUrl, HttpMethod.GET, null, new ParameterizedTypeReference <List<Account>> () {});
		
		System.out.println(accountList.getBody());
				
		return accountList.getBody();*/
		
		ResponseEntity<Account> account = restTemplate.exchange
				(accountUrl, HttpMethod.GET, null, new ParameterizedTypeReference <Account> () {});
		
		return account.getBody();
		
		/*Account[] accounts = restTemplate.getForObject(accountUrl, Account[].class);
		
		System.out.println(accounts);
		
		return Arrays.asList(accounts);*/
		
	}
	
	public String getNumberOfAccounts() {
		
		
		RestTemplate restTemplate = new RestTemplate();
		
		String response = restTemplate.getForObject(accountUrl + "/number", String.class);
		
		System.out.println(response);
		
		return response;
		
	}
		

}
