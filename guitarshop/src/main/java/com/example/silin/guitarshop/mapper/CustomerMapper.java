package com.example.silin.guitarshop.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.silin.guitarshop.model.Customer;

public class CustomerMapper implements RowMapper<Customer> {

	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customer user = new Customer();
		user.setId(rs.getLong("customer_id"));
		user.setEmail(rs.getString("email"));
		user.setHash(rs.getString("hash"));
		user.setFirstName(rs.getString("firstname"));
		user.setLastName(rs.getString("lastname"));
		user.setRegDate(rs.getTimestamp("regdate"));
		return user;
	}

}
