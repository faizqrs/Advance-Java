package com.kodewala.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kodewala.account.Account;

public class Application {

	public static void main(String[] args) {
		
		// Create IOC Container(bean factory / application context)
		String config = "applicationContext.xml";
		
		ApplicationContext context = new ClassPathXmlApplicationContext(config);
		
		// requesting or getting bean from container
		Account account = (Account) context.getBean("acc");
		
		// using the account bean
		
		System.out.println(account.getFirstName()+" and "+account.getLastName() );

	}

}
