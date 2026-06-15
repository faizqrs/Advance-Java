package com.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crud.dao.CrudRequest;
import com.crud.service.ICrudService;

@Controller
public class CrudController {

	@Autowired
	ICrudService iCrudService;
	
	
	@PostMapping("create")
	public String createMethod(@ModelAttribute CrudRequest crudRequest ) {
		iCrudService.createItem(crudRequest);
		return "success";
	}
	
	
//	@RequestMapping("read")
//	public String readMethod() {
//		return "";
//	}
//	
//	@RequestMapping("update")
//	public String updateMethod() {
//		return "";
//	}
//	
//	
//	@RequestMapping("delete")
//	public String deleteMethod() {
//		return "";
//	}
}
