package com.one_piece.thousand_sunny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ThousandSunnyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThousandSunnyApplication.class, args);
	}

}

