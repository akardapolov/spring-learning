package com.example.springboot.service;

import com.example.springboot.model.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service demonstrating conditional beans.
 *
 * This service is created only when certain conditions are met.
 */
@Slf4j
@Service
public class ConditionalBeanService {

    private final AppProperties appProperties;

    @Autowired
    public ConditionalBeanService(AppProperties appProperties) {
        this.appProperties = appProperties;
        log.info("ConditionalBeanService initialized - this bean was created conditionally");
    }

    public String getConditionalMessage() {
        if (appProperties.getFeatures().isDebugMode()) {
            return "Debug mode is enabled! Conditional bean is active.";
        } else {
            return "This conditional bean was created (without debug mode requirement).";
        }
    }

    public boolean isFeatureEnabled(String feature) {
        return switch (feature.toLowerCase()) {
            case "cache" -> appProperties.getFeatures().isCacheEnabled();
            case "metrics" -> appProperties.getFeatures().isMetricsEnabled();
            case "logging" -> appProperties.getFeatures().isLoggingEnabled();
            case "debug" -> appProperties.getFeatures().isDebugMode();
            default -> false;
        };
    }
}
