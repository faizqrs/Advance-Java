package com.kodewala.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AccountMgmt {

	@Autowired
	@Qualifier("acc1")
	private Account account;
	
	@Autowired
	private Payment payment;
	
	public void print() {
	
		account.print();
		payment.payment();
		
	}
}
