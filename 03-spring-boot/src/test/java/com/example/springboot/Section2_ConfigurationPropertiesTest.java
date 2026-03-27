package com.example.springboot;

import com.example.springboot.model.AppProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for @ConfigurationProperties.
 *
 * Demonstrates:
 * - Type-safe configuration binding
 * - Nested configuration properties
 * - Default values
 * - Validation of configuration properties
 */
@SpringBootTest
class Section2_ConfigurationPropertiesTest {

    @Autowired
    private AppProperties appProperties;

    @Test
    void applicationNameIsConfigured() {
        assertThat(appProperties.getName()).isEqualTo("Spring Boot Demo Application");
    }

    @Test
    void applicationVersionIsConfigured() {
        assertThat(appProperties.getVersion()).isEqualTo("1.0.0");
    }

    @Test
    void environmentIsSet() {
        assertThat(appProperties.getEnvironment()).isNotBlank();
    }

    @Test
    void welcomeMessageIsConfigured() {
        assertThat(appProperties.getMessage().getWelcome()).isNotBlank();
    }

    @Test
    void greetingMessageIsConfigured() {
        assertThat(appProperties.getMessage().getGreeting()).isNotBlank();
    }

    @Test
    void cacheFeatureIsEnabledByDefault() {
        assertThat(appProperties.getFeatures().isCacheEnabled()).isTrue();
    }

    @Test
    void metricsFeatureIsEnabledByDefault() {
        assertThat(appProperties.getFeatures().isMetricsEnabled()).isTrue();
    }

    @Test
    void loggingFeatureIsEnabledByDefault() {
        assertThat(appProperties.getFeatures().isLoggingEnabled()).isTrue();
    }
}
