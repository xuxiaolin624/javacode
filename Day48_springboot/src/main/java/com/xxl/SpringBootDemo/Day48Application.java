package com.xxl.SpringBootDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Day48Application extends SpringBootServletInitializer{


	public SpringApplicationBuilder confApplicationBuilder(SpringApplicationBuilder builder) {
		return builder.sources(Day48Application.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Day48Application.class, args);
	}

}
