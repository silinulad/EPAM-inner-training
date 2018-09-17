package com.example.silin.guitarshop.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.silin.guitarshop.dao.interfaces.CustomerDAO;
import com.example.silin.guitarshop.model.Customer;
import com.example.silin.guitarshop.services.interfaces.CustomerService;

@Service
@Scope("prototype")
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDAO;

	@Override
	public void create(Customer customer) {
		customerDAO.create(customer);
	}

	@Override
	public Customer read(long id) {
		return customerDAO.read(id);
	}

	@Override
	public void update(Customer customer) {
		customerDAO.update(customer);
	}

	@Override
	public void delete(long id) {
		customerDAO.delete(id);
	}

	@Override
	public List<Customer> getAll() {
		return customerDAO.getAll();
	}

}
