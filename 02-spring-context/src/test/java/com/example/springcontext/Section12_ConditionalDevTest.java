package com.example.springcontext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест с активным dev профилем для проверки условного бина.
 */
@SpringBootTest
@ActiveProfiles("dev")
class Section12_ConditionalDevTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void shouldCreateDevToolsWithDevProfile() {
        assertThat(context.containsBean("devToolsService")).isTrue();
    }
}
