package com.ssafy.withssafy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WithssafyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WithssafyApplication.class, args);
	}

}
