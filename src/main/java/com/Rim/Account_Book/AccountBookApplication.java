package com.Rim.Account_Book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class AccountBookApplication {

	public static void main(String[] args) {

        SpringApplication.run(AccountBookApplication.class, args);
	}
}
