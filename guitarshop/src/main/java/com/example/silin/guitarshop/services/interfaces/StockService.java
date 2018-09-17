package com.example.silin.guitarshop.services.interfaces;

import java.util.List;

import com.example.silin.guitarshop.model.Stock;

public interface StockService {
	void create(Stock stock);

	Stock read(long id);

	void update(Stock stock);

	void delete(long id);

	List<Stock> getAll();

	Stock readByProduct(long productId);
}
