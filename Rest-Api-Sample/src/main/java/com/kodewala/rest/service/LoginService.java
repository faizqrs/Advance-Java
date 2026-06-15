package com.kodewala.rest.service;

import org.springframework.stereotype.Service;

import com.kodewala.rest.exception.UserNotFoundException;

@Service
public class LoginService {

	public Boolean login(String user, String password) {
		
		System.out.println("LoginService.login()" +user +" " + password );
		
		// call to repo
		
		boolean isExist = checkUserWithDB(user, password);
		
		if (!isExist) {
			
			throw new UserNotFoundException("user "+user +"not found");
		}
		
		return isExist;
	}
	
	public Boolean checkUserWithDB(String user, String password) {
		
		return false;
	}
}
