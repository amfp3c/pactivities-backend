package com.capstone.capstonebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.capstone")
public class CapstoneBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapstoneBackEndApplication.class, args);
	}

}
