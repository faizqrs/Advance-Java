package com.kodewala.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.kodewala.bean.AccountMgmt;
import com.kodewala.config.SpringConfig;

public class App {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		AccountMgmt accountMgmt = context.getBean(AccountMgmt.class);
		accountMgmt.print();
		context.close();

	}

}
