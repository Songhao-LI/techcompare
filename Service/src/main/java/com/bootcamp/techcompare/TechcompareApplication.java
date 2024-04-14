package com.bootcamp.techcompare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TechcompareApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechcompareApplication.class, args);
	}
}
