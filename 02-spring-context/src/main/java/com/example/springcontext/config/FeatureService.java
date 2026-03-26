package com.example.springcontext.config;

/**
 * Сервис для демонстрации условного создания бина через @ConditionalOnProperty.
 */
public class FeatureService {

    private final String name;

    public FeatureService(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
