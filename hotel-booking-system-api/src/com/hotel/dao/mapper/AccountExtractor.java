package com.hotel.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.hotel.pojo.Account;

@Component
public class AccountExtractor implements ResultSetExtractor<Account> {

	@Override
	public Account extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		Account account = new Account();
		
		account.setAccountId(rs.getInt("account_id"));
		account.setUsername(rs.getString("user_name"));
		account.setPassword(rs.getString("pass_word"));
		account.setFullName(rs.getString("full_name"));
		account.setFullAddress(rs.getString("full_address"));
		account.setEmail(rs.getString("email"));
		account.setPhoneNumber(rs.getString("phone_number"));
		
		return account;
	}
	
}
