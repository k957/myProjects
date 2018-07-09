package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages ="com.example.assembler")
@EntityScan("com.example.model")
@EnableJpaRepositories("com.example.repository")
@ComponentScan("com.example.service")
@ComponentScan("com.example.security")
@ComponentScan("com.example.config")
@EnableCaching
public class LocalSearchDirectoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalSearchDirectoryApplication.class, args);
	}
}
