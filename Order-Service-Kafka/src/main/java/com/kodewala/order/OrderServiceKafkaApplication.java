package com.kodewala.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderServiceKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceKafkaApplication.class, args);
	}

}
