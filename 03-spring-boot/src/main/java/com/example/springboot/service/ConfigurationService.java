package com.example.springboot.service;

import com.example.springboot.exception.ConfigurationException;
import com.example.springboot.model.ApiModels.ConfigurationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service for configuration inspection.
 *
 * Demonstrates:
 * - Accessing Environment
 * - Understanding configuration sources
 * - Configuration property resolution
 */
@Slf4j
@Service
public class ConfigurationService {

    @Autowired
    private Environment environment;

    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    public String getProperty(String key) {
        String value = environment.getProperty(key);
        if (value == null) {
            throw new ConfigurationException(key, "Property not found");
        }
        return value;
    }

    public String getProperty(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }

    public List<ConfigurationInfo> getAllProperties(String prefix) {
        List<ConfigurationInfo> configs = new ArrayList<>();

        for (PropertySource<?> source : configurableEnvironment.getPropertySources()) {
            if (source.getSource() instanceof java.util.Map<?, ?> map) {
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    String key = String.valueOf(entry.getKey());
                    if (prefix == null || key.startsWith(prefix)) {
                        configs.add(ConfigurationInfo.builder()
                                .key(key)
                                .value(String.valueOf(entry.getValue()))
                                .source(source.getName())
                                .build());
                    }
                }
            }
        }

        return configs;
    }

    public List<String> getConfigurationSources() {
        List<String> sources = new ArrayList<>();
        for (PropertySource<?> source : configurableEnvironment.getPropertySources()) {
            sources.add(source.getName());
        }
        return sources;
    }

    public List<String> getActiveProfiles() {
        return List.of(environment.getActiveProfiles());
    }

    public List<String> getDefaultProfiles() {
        return List.of(environment.getDefaultProfiles());
    }
}
