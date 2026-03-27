package com.example.springboot.config;

import com.example.springboot.model.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Configuration class demonstrating conditional beans.
 *
 * Demonstrates:
 * - @ConditionalOnProperty - bean only created if property has specific value
 * - @ConditionalOnMissingBean - bean only created if no other bean of same type exists
 * - Understanding Spring Boot auto-configuration principles
 */
@Slf4j
@Configuration
public class ConditionalConfig {

    /**
     * Bean that only gets created if debug mode is enabled.
     */
    @Bean
    @ConditionalOnProperty(prefix = "app.features", name = "debug-mode", havingValue = "true")
    public DebugService debugService(AppProperties appProperties) {
        log.info("Creating DebugService - debug mode is enabled");
        return new DebugService(appProperties);
    }

    /**
     * Bean that only gets created if no other bean of this type exists.
     */
    @Bean
    @ConditionalOnMissingBean(name = "customCacheService")
    public DefaultCacheService defaultCacheService() {
        log.info("Creating DefaultCacheService - no custom cache service found");
        return new DefaultCacheService();
    }

    /**
     * Service for debug operations (conditional bean).
     */
    public static class DebugService {
        private final AppProperties appProperties;

        public DebugService(AppProperties appProperties) {
            this.appProperties = appProperties;
        }

        public String getDebugInfo() {
            return String.format("Debug Service Active - Environment: %s, Detailed Logging: %s",
                    appProperties.getEnvironment(),
                    appProperties.getFeatures().isDetailedLogging());
        }
    }

    /**
     * Default cache service implementation.
     */
    public static class DefaultCacheService {
        private static final Map<String, String> cache = new java.util.HashMap<>();

        public void put(String key, String value) {
            cache.put(key, value);
        }

        public String get(String key) {
            return cache.get(key);
        }

        public int size() {
            return cache.size();
        }

        public String getCacheInfo() {
            return "Default Cache Service - Entries: " + size();
        }
    }
}
