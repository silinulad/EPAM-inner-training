package com.example.silin.guitarshop.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.silin.guitarshop.dao.interfaces.CustomerDAO;
import com.example.silin.guitarshop.mapper.CustomerMapper;
import com.example.silin.guitarshop.model.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void create(Customer customer) {
		String sql = "INSERT INTO customers (email, hash, firstname, lastname) VALUES(?, ?, ?, ?)";
		jdbcTemplate.update(sql, customer.getEmail(), customer.getHash(), customer.getFirstName(),
				customer.getLastName());
	}

	@Override
	public Customer read(long id) {
		String sql = "SELECT * FROM customers WHERE customer_id = ?";
		return jdbcTemplate.queryForObject(sql, new CustomerMapper(), id);
	}

	@Override
	public void update(Customer customer) {
		String sql = "UPDATE customers SET email = ?, hash = ?, firstname = ?, lastname = ? WHERE customer_id = ?";
		jdbcTemplate.update(sql, customer.getEmail(), customer.getHash(), customer.getFirstName(),
				customer.getLastName());

	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM customers WHERE customer_id = ?";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public List<Customer> getAll() {
		String sql = "SELECT * FROM customers";
		return jdbcTemplate.query(sql, new CustomerMapper());
	}
}
