package com.example.springboot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Common API models used across the application.
 */
public class ApiModels {

    /**
     * Standard API response wrapper.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;
        private List<String> errors;
        private LocalDateTime timestamp;

        public static <T> ApiResponse<T> success(T data) {
            return ApiResponse.<T>builder()
                    .success(true)
                    .data(data)
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        public static <T> ApiResponse<T> success(String message, T data) {
            return ApiResponse.<T>builder()
                    .success(true)
                    .message(message)
                    .data(data)
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        public static <T> ApiResponse<T> error(String message) {
            return ApiResponse.<T>builder()
                    .success(false)
                    .message(message)
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        public static <T> ApiResponse<T> error(String message, List<String> errors) {
            return ApiResponse.<T>builder()
                    .success(false)
                    .message(message)
                    .errors(errors)
                    .timestamp(LocalDateTime.now())
                    .build();
        }
    }

    /**
     * Application info model.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AppInfo {
        private String name;
        private String version;
        private String environment;
        private List<String> features;
    }

    /**
     * Bean information model.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BeanInfo {
        private String name;
        private String type;
        private String scope;
        private boolean isSingleton;
        private boolean isPrototype;
    }

    /**
     * Configuration info model.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ConfigurationInfo {
        private String key;
        private String value;
        private String source;
    }

    /**
     * Profile info model.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProfileInfo {
        private List<String> activeProfiles;
        private List<String> defaultProfiles;
        private String currentProfile;
    }

    /**
     * Metrics info model.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MetricsInfo {
        private int beanCount;
        private int endpointCount;
        private Map<String, Object> customMetrics;
    }

    /**
     * Condition info model.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ConditionInfo {
        private String conditionName;
        private boolean matched;
        private String reason;
        private String beanName;
    }
}
