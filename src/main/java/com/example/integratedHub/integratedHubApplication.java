package com.example.integratedHub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.example")
@MapperScan("com.example.integratedHub.dao")
@SpringBootApplication
public class integratedHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(integratedHubApplication.class, args);
	}

}
