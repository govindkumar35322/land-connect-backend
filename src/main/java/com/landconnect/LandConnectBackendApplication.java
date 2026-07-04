package com.landconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LandConnectBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LandConnectBackendApplication.class, args);
		System.out.println("land connect db");
	}

}
