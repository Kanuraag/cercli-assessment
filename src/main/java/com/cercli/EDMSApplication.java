package com.cercli;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class EDMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(EDMSApplication.class, args);
	}

}