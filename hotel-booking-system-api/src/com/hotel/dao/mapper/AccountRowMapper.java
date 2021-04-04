package com.hotel.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hotel.pojo.Account;

@Component
public class AccountRowMapper implements RowMapper<Account> {
	
	private AccountExtractor accountExtractor;
	
	@Autowired
	public void setAccountExtractor(AccountExtractor accountExtractor) {
		this.accountExtractor = accountExtractor;
	}
	
	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		return accountExtractor.extractData(rs);
	}
	
}
