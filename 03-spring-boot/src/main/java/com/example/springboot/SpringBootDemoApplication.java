package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Main application class for Spring Boot Demo.
 *
 * Demonstrates:
 * - @SpringBootApplication meta-annotation
 * - @ConfigurationPropertiesScan for property binding
 * - Application entry point
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }
}
