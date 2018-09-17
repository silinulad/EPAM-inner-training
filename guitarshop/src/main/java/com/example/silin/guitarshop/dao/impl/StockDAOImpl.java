package com.example.silin.guitarshop.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.silin.guitarshop.dao.interfaces.StockDAO;
import com.example.silin.guitarshop.mapper.StockMapper;
import com.example.silin.guitarshop.model.Stock;

@Repository
public class StockDAOImpl implements StockDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void create(Stock stock) {
		String sql = "INSERT INTO stock (product_id) VALUES(?)";
		jdbcTemplate.update(sql, stock.getProduct().getId());
	}

	@Override
	public Stock read(long id) {
		String sql = "SELECT * FROM stock " + "JOIN products AS p ON p.product_id = stock.product_id "
				+ "JOIN models AS m ON m.model_id = p.model_id "
				+ "JOIN categories AS c ON c.category_id = m.category_id "
				+ "JOIN makers AS m1 ON m1.maker_id = m.maker_id WHERE stock.item_id = ?";
		return jdbcTemplate.queryForObject(sql, new StockMapper(), id);
	}

	@Override
	public void update(Stock stock) {
		update(stock);
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM stock WHERE product_id = ?";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public List<Stock> getAll() {
		String sql = "SELECT * FROM stock " + "JOIN products AS p ON p.product_id = stock.product_id "
				+ "JOIN models AS m ON m.model_id = p.model_id "
				+ "JOIN categories AS c ON c.category_id = m.category_id "
				+ "JOIN makers AS m1 ON m1.maker_id = m.maker_id";
		return jdbcTemplate.query(sql, new StockMapper());
	}

	@Override
	public Stock readByProduct(long productId) {
		String sql = "SELECT * FROM stock " + "JOIN products AS p ON p.product_id = stock.product_id "
				+ "JOIN models AS m ON m.model_id = p.model_id "
				+ "JOIN categories AS c ON c.category_id = m.category_id "
				+ "JOIN makers AS m1 ON m1.maker_id = m.maker_id WHERE stock.product_id = ? LIMIT 1";
		try {
			Stock stock = jdbcTemplate.queryForObject(sql, new StockMapper(), productId);
			return stock;
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException("A product with id " + productId + " ran out", 1);
		}
	}

}
