package com.example.silin.guitarshop.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.silin.guitarshop.dao.interfaces.OrderDAO;
import com.example.silin.guitarshop.model.Order;
import com.example.silin.guitarshop.model.Product;
import com.example.silin.guitarshop.services.interfaces.OrderService;

@Service
@Scope("prototype")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDAO orderDAO;

	@Override
	public void create(Order order) {
		orderDAO.create(order);
	}

	@Override
	public Order read(long id) {
		return orderDAO.read(id);
	}

	@Override
	public void update(Order order) {
		orderDAO.update(order);
	}

	@Override
	public void delete(long id) {
		orderDAO.delete(id);
	}

	@Override
	public List<Order> getAll() {
		return orderDAO.getAll();
	}

	@Override
	public List<Order> getAllCustomerOrders(long id) {
		return orderDAO.getAllCustomerOrders(id);
	}

	@Override
	public void completeOrder(long orderId) {
		orderDAO.completeOrder(orderId);
	}

	@Override
	public Order getLastCustomerOrder(long customerId) {
		return orderDAO.geLastCustomerOrder(customerId);
	}

	@Override
	public List<Product> getProductsForOrder(long orderId) {
		return orderDAO.getProductsForOrder(orderId);
	}

	@Override
	public void deleteProductFromOrder(long orderId, long productId) {
		orderDAO.deleteProductFromOrder(orderId, productId);
	}

	@Override
	public void addProductToOrder(long orderId, long productId) {
		orderDAO.addProductToOrder(orderId, productId);
	}

}
