package com.crud.service;

import java.math.BigDecimal;
import java.util.List;

import com.crud.dao.CrudRequest;
import com.crud.dao.CrudResponse;

public interface ICrudService {

	public void createItem(CrudRequest crudRequest);
//	public List<CrudResponse> readItem();
//	public CrudResponse getItemById(int id);
//	public void updateItem(int id, String name, BigDecimal price, String status);
//	public void deleteItem(int id);
	
}
