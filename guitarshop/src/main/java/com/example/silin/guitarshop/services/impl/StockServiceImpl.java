package com.example.silin.guitarshop.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.silin.guitarshop.dao.interfaces.StockDAO;
import com.example.silin.guitarshop.model.Stock;
import com.example.silin.guitarshop.services.interfaces.StockService;

@Service
@Scope("prototype")
public class StockServiceImpl implements StockService {
	@Autowired
	private StockDAO stockDAO;

	@Override
	public void create(Stock stock) {
		stockDAO.create(stock);
	}

	@Override
	public Stock read(long id) {
		return stockDAO.read(id);
	}

	@Override
	public void update(Stock stock) {
		stockDAO.update(stock);
	}

	@Override
	public void delete(long id) {
		stockDAO.delete(id);
	}

	@Override
	public List<Stock> getAll() {
		return stockDAO.getAll();
	}

	@Override
	public Stock readByProduct(long productId) {
		return stockDAO.readByProduct(productId);
	}

}
