package com.hotel.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hotel.pojo.HotelDate;

@Component
public class DateRowMapper implements RowMapper<HotelDate> {
	
	private DateExtractor dateExtractor;
	
	@Autowired
	public void setDateExtractor(DateExtractor dateExtractor) {
		this.dateExtractor = dateExtractor;
	}
	
	@Override
	public HotelDate mapRow(ResultSet rs, int rowNum) throws SQLException {
		return dateExtractor.extractData(rs);
	}
	
}