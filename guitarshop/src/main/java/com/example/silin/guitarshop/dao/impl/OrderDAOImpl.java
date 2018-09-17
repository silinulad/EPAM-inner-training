package com.example.silin.guitarshop.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.silin.guitarshop.dao.interfaces.OrderDAO;
import com.example.silin.guitarshop.mapper.OrderMapper;
import com.example.silin.guitarshop.mapper.ProductMapper;
import com.example.silin.guitarshop.model.Order;
import com.example.silin.guitarshop.model.Product;

@Repository
public class OrderDAOImpl implements OrderDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void create(Order order) {
		String sql = "INSERT INTO orders (customer_id) VALUES(?)";
		jdbcTemplate.update(sql, order.getCustomer().getId());
	}

	@Override
	public Order read(long id) {
		String sql = "SELECT * FROM orders JOIN customers AS c1 ON c1.customer_id = orders.customer_id WHERE order_id = ?";
		return jdbcTemplate.queryForObject(sql, new OrderMapper(), id);
	}

	@Override
	public void update(Order order) {
		String sql = "UPDATE orders SET total_value = ? WHERE order_id = ?";
		jdbcTemplate.update(sql, order.getTotaValue(), order.getId());
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM orders WHERE order_id = ?";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public List<Order> getAll() {
		String sql = "SELECT * FROM orders AS o " + "JOIN customers AS c ON c.customer_id = o.customer_id "
				+ "WHERE o.complete = 0 ORDER BY o.order_time DESC";
		return jdbcTemplate.query(sql, new OrderMapper());
	}

	@Override
	public List<Order> getAllCustomerOrders(long id) {
		String sql = "SELECT * FROM orders JOIN customers AS c1 ON c1.customer_id = orders.customer_id JOIN order_products AS op ON op.order_id = orders.order_id WHERE c1.customer_id = ?";
		return jdbcTemplate.query(sql, new OrderMapper(), id);
	}

	@Override
	public void completeOrder(long orderId) {
		String sql = "UPDATE orders SET complete = 1 WHERE order_id = ?";
		jdbcTemplate.update(sql, orderId);
	}

	@Override
	public Order geLastCustomerOrder(long customerId) {
		String sql = "SELECT * FROM orders JOIN customers AS c1 ON c1.customer_id = orders.customer_id WHERE complete = 0 AND c1.customer_id = ? ORDER BY order_time DESC LIMIT 1";
		try {
			Order order = jdbcTemplate.queryForObject(sql, new OrderMapper(), customerId);
			return order;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public List<Product> getProductsForOrder(long orderId) {
		String sql = "SELECT * FROM order_products AS op " + "JOIN products AS p ON p.product_id = op.product_id "
				+ "JOIN models AS m ON m.model_id = p.model_id "
				+ "JOIN categories AS c ON c.category_id = m.category_id "
				+ "JOIN makers AS m1 ON m1.maker_id = m.maker_id " + "WHERE op.order_id = ? ORDER BY op.add_time DESC";

		return jdbcTemplate.query(sql, new ProductMapper(), orderId);
	}

	@Override
	public void deleteProductFromOrder(long orderId, long productId) {
		String sql = "DELETE FROM order_products WHERE order_id = ? AND product_id = ? LIMIT 1";
		jdbcTemplate.update(sql, orderId, productId);
	}

	@Override
	public void addProductToOrder(long orderId, long productId) {
		String sql = "INSERT INTO order_products (order_id, product_id) VALUES(?, ?)";
		jdbcTemplate.update(sql, orderId, productId);
	}

}
