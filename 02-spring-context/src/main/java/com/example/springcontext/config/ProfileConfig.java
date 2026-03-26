package com.example.springcontext.config;

import com.example.springcontext.entity.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

/**
 * Конфигурация для демонстрации профилей Spring.
 */
@Configuration
public class ProfileConfig {

    /**
     * Бин для dev профиля.
     */
    @Bean
    @Profile("dev")
    public Product devProduct() {
        return new Product("Dev Product", "DEV", BigDecimal.valueOf(0.00));
    }

    /**
     * Бин для prod профиля.
     */
    @Bean
    @Profile("prod")
    public Product prodProduct() {
        return new Product("Prod Product", "PROD", BigDecimal.valueOf(999.99));
    }

    /**
     * Бин для тестового профиля.
     */
    @Bean
    @Profile("test")
    public Product testProduct() {
        return new Product("Test Product", "TEST", BigDecimal.valueOf(1.00));
    }
}
