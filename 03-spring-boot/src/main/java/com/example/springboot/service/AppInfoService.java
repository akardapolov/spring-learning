package com.example.springboot.service;

import com.example.springboot.model.AppProperties;
import com.example.springboot.model.ApiModels;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service for application information.
 *
 * Demonstrates:
 * - @Service annotation
 * - Constructor injection with @RequiredArgsConstructor
 * - Accessing application properties
 * - Environment access
 */
@Service
@RequiredArgsConstructor
public class AppInfoService {

    private final AppProperties appProperties;

    @Autowired
    private Environment environment;

    public ApiModels.AppInfo getAppInfo() {
        return ApiModels.AppInfo.builder()
                .name(appProperties.getName())
                .version(appProperties.getVersion())
                .environment(appProperties.getEnvironment())
                .features(getEnabledFeatures())
                .build();
    }

    public String getWelcomeMessage() {
        return appProperties.getMessage().getWelcome();
    }

    public String getGreetingMessage() {
        return appProperties.getMessage().getGreeting();
    }

    public List<String> getActiveProfiles() {
        return Arrays.asList(environment.getActiveProfiles());
    }

    public String getCurrentProfile() {
        return getActiveProfiles().isEmpty()
                ? "default"
                : getActiveProfiles().get(0);
    }

    private List<String> getEnabledFeatures() {
        return Arrays.asList(
                "Cache: " + appProperties.getFeatures().isCacheEnabled(),
                "Metrics: " + appProperties.getFeatures().isMetricsEnabled(),
                "Logging: " + appProperties.getFeatures().isLoggingEnabled(),
                "Debug Mode: " + appProperties.getFeatures().isDebugMode()
        );
    }
}
