package com.example.silin.guitarshop.services.interfaces;

import java.util.List;

import com.example.silin.guitarshop.model.Product;

public interface ProductService {
	void create(Product product);

	Product read(long id);

	void update(Product product);

	void delete(long id);

	List<Product> getAll();

}
