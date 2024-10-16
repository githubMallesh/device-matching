package com.experian.devicematching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.experian.*"})
//@EnableJpaRepositories(basePackages = "com.*")
@EntityScan(basePackages = {"com.*"})
public class DeviceMatchingApplication {
	
	
	public static void main(String[] args) {
		
		SpringApplication.run(DeviceMatchingApplication.class, args);
		
	}

	
	



}
