package com.joshuahawatta.moneyzilla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class MoneyzillaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MoneyzillaApplication.class, args);
	}
}
