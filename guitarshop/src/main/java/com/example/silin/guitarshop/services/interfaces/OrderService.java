package com.example.silin.guitarshop.services.interfaces;

import java.util.List;

import com.example.silin.guitarshop.model.Order;
import com.example.silin.guitarshop.model.Product;

public interface OrderService {
	void create(Order order);

	Order read(long id);

	void update(Order order);

	void delete(long id);

	List<Order> getAll();

	List<Order> getAllCustomerOrders(long id);

	void completeOrder(long orderId);

	Order getLastCustomerOrder(long customerId);

	List<Product> getProductsForOrder(long orderId);

	void deleteProductFromOrder(long orderId, long productId);

	void addProductToOrder(long orderId, long productId);

}
