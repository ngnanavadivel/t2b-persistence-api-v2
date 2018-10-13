package com.t2b.api.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class T2bPersistenceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(T2bPersistenceApiApplication.class, args);
	}
}
