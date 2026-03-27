package com.example.springboot.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * Configuration for custom metrics.
 *
 * Demonstrates:
 * - Micrometer integration
 * - Custom metrics registration
 * - Actuator metrics extension
 */
@Configuration
public class CustomMetricsConfig {

    @Autowired
    private MeterRegistry meterRegistry;

    private Counter requestCounter;

    @PostConstruct
    public void initMetrics() {
        requestCounter = Counter.builder("api.requests.total")
                .description("Total number of API requests")
                .tag("type", "demo")
                .register(meterRegistry);

        meterRegistry.gauge("app.beans.loaded", 0);
    }

    public void recordRequest() {
        requestCounter.increment();
    }

    public Counter getRequestCounter() {
        return requestCounter;
    }
}
