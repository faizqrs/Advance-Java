package com.kodewala.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodewala.rest.request.UserRequest;
import com.kodewala.rest.response.UserResponse;
import com.kodewala.rest.service.LoginService;

@RestController  // @Controller + @ResponseBody
@RequestMapping("user/api/")
public class UserController {

	@Autowired
	LoginService loginService;
	
	@GetMapping("login")
	public ResponseEntity<UserResponse> doLogin(@RequestBody UserRequest request) {
		
		System.out.println("User :"+request.getUserId() );
		
		loginService.login(request.getUserId(), request.getPassword());
		
		UserResponse response = new UserResponse();
		response.setMessage("LoggedIn Success");
		return ResponseEntity.ok(response);
	}
}
