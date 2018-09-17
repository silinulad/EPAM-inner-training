package com.example.silin.guitarshop.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.silin.guitarshop.model.Product;
import com.example.silin.guitarshop.model.ProductCategory;
import com.example.silin.guitarshop.model.ProductMaker;
import com.example.silin.guitarshop.model.ProductModel;

public class ProductMapper implements RowMapper<Product> {

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductCategory category = new ProductCategory();
		category.setId(rs.getLong("category_id"));
		category.setName(rs.getString("category_name"));
		category.setDescription(rs.getString("category_description"));

		ProductMaker maker = new ProductMaker();
		maker.setId(rs.getLong("maker_id"));
		maker.setName(rs.getString("maker_name"));
		maker.setCountry(rs.getString("country"));

		ProductModel model = new ProductModel();
		model.setId(rs.getLong("model_id"));
		model.setCategory(category);
		model.setMaker(maker);
		model.setName(rs.getString("model_name"));
		model.setDescription(rs.getString("model_description"));
		model.setImage(rs.getString("image"));

		Product product = new Product();
		product.setId(rs.getLong("product_id"));
		product.setModel(model);
		product.setPrice(rs.getDouble("price"));

		return product;
	}

}
