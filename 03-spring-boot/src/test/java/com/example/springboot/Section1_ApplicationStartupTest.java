package com.example.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for Spring Boot application startup.
 *
 * Demonstrates:
 * - @SpringBootTest annotation
 * - Testing application context loads
 * - Basic integration testing
 */
@SpringBootTest
class Section1_ApplicationStartupTest {

    @Autowired
    private SpringBootDemoApplication application;

    @Test
    void contextLoads() {
        // This test verifies that the Spring application context loads successfully
        assertThat(application).isNotNull();
    }
}
