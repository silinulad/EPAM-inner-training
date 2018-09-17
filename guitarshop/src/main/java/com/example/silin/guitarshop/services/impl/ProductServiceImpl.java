package com.example.silin.guitarshop.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.silin.guitarshop.dao.interfaces.ProductDAO;
import com.example.silin.guitarshop.model.Product;
import com.example.silin.guitarshop.services.interfaces.ProductService;

@Service
@Scope("prototype")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDAO;

	@Override
	public void create(Product product) {
		productDAO.create(product);
	}

	@Override
	public Product read(long id) {
		return productDAO.read(id);
	}

	@Override
	public void update(Product product) {
		productDAO.update(product);
	}

	@Override
	public void delete(long id) {
		productDAO.delete(id);
	}

	@Override
	public List<Product> getAll() {
		return productDAO.getAll();
	}

}
