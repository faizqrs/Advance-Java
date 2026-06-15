package com.crud.service.impl;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.dao.CrudRequest;
import com.crud.dao.CrudResponse;
import com.crud.entity.CrudEntity;
import com.crud.repository.ICrudRepository;
import com.crud.service.ICrudService;


@Service
public class CrudServiceImpl implements ICrudService{

	
	@Autowired
	private ICrudRepository crudRepository;
	
	@Override
	public void createItem(CrudRequest crudRequest) {
		
		CrudEntity entity = new CrudEntity();
		entity.setName(crudRequest.getItemName());
		entity.setPrice(crudRequest.getItemPrice());
		entity.setStatus(crudRequest.getItemStatus());
		crudRepository.save(entity);
	}

//	@Override
//	public List<CrudResponse> readItem() {
//		
//		return null;
//	}
//
//	@Override
//	public CrudResponse getItemById(int id) {
//		
//		return null;
//	}
//
//	@Override
//	public void updateItem(int id, String name, BigDecimal price, String status) {
//		
//		
//	}
//
//	@Override
//	public void deleteItem(int id) {
//		
//		
//	}

	

	
	

}
