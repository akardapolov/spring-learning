package com.example.springcontext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Точка входа Spring Boot приложения.
 * {@code @SpringBootApplication} включает в себя {@code @Configuration},
 * {@code @EnableAutoConfiguration} и {@code @ComponentScan}.
 */
@SpringBootApplication
public class SpringContextApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringContextApplication.class, args);
    }
}
