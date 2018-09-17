package com.example.silin.guitarshop.dao.interfaces;

import java.util.List;

public interface GenericDAO<T> {

	void create(T t);

	T read(long id);

	void update(T t);

	void delete(long id);

	List<T> getAll();

}
