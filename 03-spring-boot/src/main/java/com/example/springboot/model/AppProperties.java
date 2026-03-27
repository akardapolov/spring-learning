package com.example.springboot.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Configuration properties for the application.
 *
 * Demonstrates:
 * - @ConfigurationProperties for type-safe configuration binding
 * - @Validated for validation of configuration properties
 * - Nested configuration properties
 */
@Data
@Validated
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    @NotBlank(message = "Application name cannot be blank")
    private String name;

    @NotBlank(message = "Application version cannot be blank")
    private String version;

    @NotNull
    private String environment;

    private Message message = new Message();

    private Features features = new Features();

    @Data
    public static class Message {
        @NotBlank
        private String welcome;

        @NotBlank
        private String greeting;
    }

    @Data
    public static class Features {
        private boolean cacheEnabled = true;
        private boolean metricsEnabled = true;
        private boolean loggingEnabled = true;
        private boolean debugMode = false;
        private boolean detailedLogging = false;
    }
}
