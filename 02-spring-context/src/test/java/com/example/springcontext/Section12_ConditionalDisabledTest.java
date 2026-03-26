package com.example.springcontext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест с отключенным свойством feature.enabled.
 */
@SpringBootTest
@TestPropertySource(properties = "feature.enabled=false")
class Section12_ConditionalDisabledTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void shouldNotCreateBeanWhenPropertyFalse() {
        assertThat(context.containsBean("enabledFeatureService")).isFalse();
    }
}
