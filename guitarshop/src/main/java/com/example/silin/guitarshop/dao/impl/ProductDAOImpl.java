package com.example.silin.guitarshop.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.silin.guitarshop.dao.interfaces.ProductDAO;
import com.example.silin.guitarshop.mapper.ProductMapper;
import com.example.silin.guitarshop.model.Product;

@Repository
public class ProductDAOImpl implements ProductDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void create(Product product) {
		String sql = "INSERT INTO products (model_id, price) VALUES(?, ?)";
		jdbcTemplate.update(sql, product.getModel().getId(), product.getPrice());
	}

	@Override
	public Product read(long id) {
		String sql = "SELECT * FROM products " + "JOIN models AS m1 ON m1.model_id = products.model_id "
				+ "JOIN categories AS c1 ON c1.category_id = m1.category_id "
				+ "JOIN makers AS m2 ON m2.maker_id = m1.maker_id " + "WHERE product_id = ?";
		return jdbcTemplate.queryForObject(sql, new ProductMapper(), id);
	}

	@Override
	public void update(Product product) {
		String sql = "UPDATE products SET price = ? WHERE product_id = ?";
		jdbcTemplate.update(sql, product.getPrice(), product.getId());
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM products WHERE product_id = ?";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public List<Product> getAll() {
		String sql = "SELECT * FROM products " + "JOIN models AS m1 ON m1.model_id = products.model_id "
				+ "JOIN categories AS c1 ON c1.category_id = m1.category_id "
				+ "JOIN makers AS m2 ON m2.maker_id = m1.maker_id";
		return jdbcTemplate.query(sql, new ProductMapper());
	}

}
