package com.example.silin.guitarshop.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.silin.guitarshop.model.Product;
import com.example.silin.guitarshop.model.Stock;

public class StockMapper implements RowMapper<Stock> {

	@Override
	public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product product = new ProductMapper().mapRow(rs, rowNum);
		Stock stock = new Stock();
		stock.setId(rs.getLong("item_id"));
		stock.setProduct(product);
		stock.setUpdateTime(rs.getTimestamp("update_time"));
		return stock;
	}

}
