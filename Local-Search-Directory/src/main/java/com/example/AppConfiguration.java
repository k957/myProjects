package com.example;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EntityScan("com.example.model")
@EnableJpaRepositories("com.example.repository")
@EnableCaching
@EnableWebMvc
@ComponentScan(basePackages = {"com.example.service","com.example.security", "com.example.config", "com.example.assembler"})
public class AppConfiguration {
}
