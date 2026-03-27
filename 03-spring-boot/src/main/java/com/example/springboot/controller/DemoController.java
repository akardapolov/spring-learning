package com.example.springboot.controller;

import com.example.springboot.model.ApiModels;
import com.example.springboot.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main demo controller for Spring Boot demonstrations.
 *
 * Demonstrates:
 * - @RestController annotation
 * - HTTP methods mapping (@GetMapping, @PostMapping, etc.)
 * - Request parameters and path variables
 * - Response handling
 */
@RestController
@RequestMapping("/api/demo")
@RequiredArgsConstructor
public class DemoController {

    private final AppInfoService appInfoService;
    private final BeanService beanService;
    private final ConfigurationService configurationService;
    private final ConditionalBeanService conditionalBeanService;
    private final ProfileService profileService;

    // ==================== Application Info ====================

    @GetMapping("/app-info")
    public ResponseEntity<ApiModels.ApiResponse<ApiModels.AppInfo>> getAppInfo() {
        return ResponseEntity.ok(ApiModels.ApiResponse.success(appInfoService.getAppInfo()));
    }

    @GetMapping("/welcome")
    public ResponseEntity<ApiModels.ApiResponse<String>> getWelcomeMessage() {
        return ResponseEntity.ok(ApiModels.ApiResponse.success(appInfoService.getWelcomeMessage()));
    }

    @GetMapping("/greeting")
    public ResponseEntity<ApiModels.ApiResponse<String>> getGreeting(
            @RequestParam(defaultValue = "Guest") String name) {
        String greeting = appInfoService.getGreetingMessage() + ", " + name + "!";
        return ResponseEntity.ok(ApiModels.ApiResponse.success(greeting));
    }

    // ==================== Bean Inspection ====================

    @GetMapping("/beans")
    public ResponseEntity<ApiModels.ApiResponse<Map<String, Object>>> getAllBeans() {
        Map<String, Object> result = new HashMap<>();
        result.put("totalBeans", beanService.getBeanCount());
        result.put("beans", beanService.getAllBeans().stream()
                .limit(50)
                .toList());
        return ResponseEntity.ok(ApiModels.ApiResponse.success(result));
    }

    @GetMapping("/beans/{beanName}")
    public ResponseEntity<ApiModels.ApiResponse<ApiModels.BeanInfo>> getBeanInfo(
            @PathVariable String beanName) {
        return ResponseEntity.ok(ApiModels.ApiResponse.success(beanService.getBeanInfo(beanName)));
    }

    @GetMapping("/beans/count")
    public ResponseEntity<ApiModels.ApiResponse<Long>> getBeanCount() {
        return ResponseEntity.ok(ApiModels.ApiResponse.success(beanService.getBeanCount()));
    }

    // ==================== Configuration ====================

    @GetMapping("/config/property")
    public ResponseEntity<ApiModels.ApiResponse<String>> getProperty(
            @RequestParam String key) {
        return ResponseEntity.ok(ApiModels.ApiResponse.success(configurationService.getProperty(key)));
    }

    @GetMapping("/config/sources")
    public ResponseEntity<ApiModels.ApiResponse<List<String>>> getConfigurationSources() {
        return ResponseEntity.ok(ApiModels.ApiResponse.success(configurationService.getConfigurationSources()));
    }

    @GetMapping("/config/app")
    public ResponseEntity<ApiModels.ApiResponse<Map<String, Object>>> getAppConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put("name", appInfoService.getAppInfo().getName());
        config.put("version", appInfoService.getAppInfo().getVersion());
        config.put("environment", appInfoService.getAppInfo().getEnvironment());
        config.put("activeProfiles", profileService.getActiveProfiles());
        return ResponseEntity.ok(ApiModels.ApiResponse.success(config));
    }

    // ==================== Profiles ====================

    @GetMapping("/profiles")
    public ResponseEntity<ApiModels.ApiResponse<ApiModels.ProfileInfo>> getProfiles() {
        return ResponseEntity.ok(ApiModels.ApiResponse.success(profileService.getProfileInfo()));
    }

    @GetMapping("/profiles/current")
    public ResponseEntity<ApiModels.ApiResponse<String>> getCurrentProfile() {
        return ResponseEntity.ok(ApiModels.ApiResponse.success(profileService.getCurrentProfile()));
    }

    @GetMapping("/profiles/message")
    public ResponseEntity<ApiModels.ApiResponse<String>> getProfileMessage() {
        return ResponseEntity.ok(ApiModels.ApiResponse.success(profileService.getProfileSpecificMessage()));
    }

    // ==================== Conditional Beans ====================

    @GetMapping("/conditional/message")
    public ResponseEntity<ApiModels.ApiResponse<String>> getConditionalMessage() {
        return ResponseEntity.ok(ApiModels.ApiResponse.success(conditionalBeanService.getConditionalMessage()));
    }

    @GetMapping("/conditional/feature/{feature}")
    public ResponseEntity<ApiModels.ApiResponse<Boolean>> isFeatureEnabled(
            @PathVariable String feature) {
        return ResponseEntity.ok(ApiModels.ApiResponse.success(conditionalBeanService.isFeatureEnabled(feature)));
    }

    // ==================== Health Check ====================

    @GetMapping("/health/custom")
    public ResponseEntity<ApiModels.ApiResponse<Map<String, Object>>> customHealth() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("application", appInfoService.getAppInfo());
        health.put("profile", profileService.getCurrentProfile());
        health.put("beanCount", beanService.getBeanCount());
        return ResponseEntity.ok(ApiModels.ApiResponse.success(health));
    }
}
