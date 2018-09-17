package com.example.silin.guitarshop.services.interfaces;

import java.util.List;

import com.example.silin.guitarshop.model.Customer;

public interface CustomerService {

	void create(Customer customer);

	Customer read(long id);

	void update(Customer customer);

	void delete(long id);

	List<Customer> getAll();
}
