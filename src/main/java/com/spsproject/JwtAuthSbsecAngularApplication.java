package com.spsproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories
@SpringBootApplication
public class JwtAuthSbsecAngularApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthSbsecAngularApplication.class, args);
	}
}
