package com.example.silin.guitarshop.dao.interfaces;

import com.example.silin.guitarshop.model.Stock;

public interface StockDAO extends GenericDAO<Stock> {

	Stock readByProduct(long productId);

}
