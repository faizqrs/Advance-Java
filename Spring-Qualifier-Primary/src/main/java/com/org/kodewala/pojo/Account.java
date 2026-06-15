package com.org.kodewala.pojo;


public class Account {

	private String name;
	private String type;
	
	public Account() {
		
	}
	
	public Account(String _name, String _type) {
		this.name = _name;
		this.type = _type;
	}	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
