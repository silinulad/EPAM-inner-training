package com.example.silin.guitarshop.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.silin.guitarshop.model.Customer;
import com.example.silin.guitarshop.model.Order;

public class OrderMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customer customer = new CustomerMapper().mapRow(rs, rowNum);
		Order order = new Order();
		order.setId(rs.getLong("order_id"));
		order.setCustomer(customer);
		order.setComplete(rs.getBoolean("complete"));
		order.setTime(rs.getTimestamp("order_time"));
		order.setTotaValue(rs.getDouble("total_value"));
		return order;
	}

}
