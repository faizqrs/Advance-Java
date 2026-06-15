package com.crud.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "crud_table")
public class CrudEntity {

	
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id", length = 6)
	private int id;
	
	@Column(name = "item_name")
	private String name;
	
	@Column(name = "item_price")
	private BigDecimal price;
	
	@Column(name = "item_status")
	private String status = "Available";

	
	public CrudEntity() {
		
	}
	
	public CrudEntity(int id, String name, BigDecimal price, String status) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
