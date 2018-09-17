package com.example.silin.guitarshop.dao.interfaces;

import java.util.List;

import com.example.silin.guitarshop.model.Order;
import com.example.silin.guitarshop.model.Product;

public interface OrderDAO extends GenericDAO<Order> {

	List<Order> getAllCustomerOrders(long id);

	void completeOrder(long orderId);

	Order geLastCustomerOrder(long customerId);

	List<Product> getProductsForOrder(long orderId);

	void deleteProductFromOrder(long orderId, long productId);

	void addProductToOrder(long orderId, long productId);

}
