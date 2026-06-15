package com.org.kodewala.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.org.kodewala.config.SpringConfig;
import com.org.kodewala.pojo.AccountMgmt;

public class App {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		AccountMgmt accountMgmt = context.getBean(AccountMgmt.class);
		accountMgmt.Print();
		context.close();

	}

}
