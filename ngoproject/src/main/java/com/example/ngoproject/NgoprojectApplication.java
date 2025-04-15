package com.example.ngoproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


@SpringBootApplication
@EnableMethodSecurity
public class NgoprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NgoprojectApplication.class, args);
	}

}
