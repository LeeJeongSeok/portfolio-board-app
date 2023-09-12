package com.jeongseok.portfolioboardapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PortfolioBoardAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioBoardAppApplication.class, args);
	}

}
