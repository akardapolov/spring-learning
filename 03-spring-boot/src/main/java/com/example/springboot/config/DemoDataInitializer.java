package com.example.springboot.config;

import com.example.springboot.service.BeanService;
import com.example.springboot.service.ConditionalBeanService;
import com.example.springboot.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Demo data initializer that runs after application startup.
 *
 * Demonstrates:
 * - CommandLineRunner for startup logic
 * - Initializing data after context is ready
 * - Using application context services
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class DemoDataInitializer {

    @Bean
    public CommandLineRunner initializeDemoData(
            ProfileService profileService,
            BeanService beanService,
            ConditionalBeanService conditionalBeanService) {

        return args -> {
            log.info("========================================");
            log.info("Spring Boot Demo Application Started!");
            log.info("========================================");
            log.info("Current Profile: {}", profileService.getCurrentProfile());
            log.info("Active Profiles: {}", profileService.getActiveProfiles());
            log.info("Total Beans: {}", beanService.getBeanCount());
            log.info("Cache Enabled: {}", conditionalBeanService.isFeatureEnabled("cache"));
            log.info("Metrics Enabled: {}", conditionalBeanService.isFeatureEnabled("metrics"));
            log.info("Debug Mode: {}", conditionalBeanService.isFeatureEnabled("debug"));
            log.info("========================================");
        };
    }
}
