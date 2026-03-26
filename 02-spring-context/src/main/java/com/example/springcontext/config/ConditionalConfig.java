package com.example.springcontext.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Конфигурация для демонстрации условных бинов.
 */
@Configuration
public class ConditionalConfig {

    /**
     * Бин создается только если свойство feature.enabled=true.
     * При отсутствии свойства бин создаётся (matchIfMissing=true).
     */
    @Bean
    @ConditionalOnProperty(name = "feature.enabled", havingValue = "true", matchIfMissing = true)
    public FeatureService enabledFeatureService() {
        return new FeatureService("ENABLED_FEATURE");
    }

    /**
     * Бин создается только в dev профиле.
     */
    @Bean
    @Profile("dev")
    public DevToolsService devToolsService() {
        return new DevToolsService();
    }
}
