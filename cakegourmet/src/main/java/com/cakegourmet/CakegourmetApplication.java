package com.cakegourmet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CakegourmetApplication {

	public static void main(String[] args) {
		SpringApplication.run(CakegourmetApplication.class, args);
		System.out.print("\n\n\n");
		System.out.print(new BCryptPasswordEncoder().encode("123456"));
	}

}
