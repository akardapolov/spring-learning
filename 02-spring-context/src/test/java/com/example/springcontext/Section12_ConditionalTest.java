package com.example.springcontext;

import com.example.springcontext.model.ApiModels.ConditionalResult;
import com.example.springcontext.service.SpringContextDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Раздел 12: Демонстрация условных бинов в Spring.
 * {@code @ConditionalOnProperty}, {@code @ConditionalOnClass} и др. позволяют создавать
 * бины только при выполнении определенных условий.
 */
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section12_ConditionalTest {

    private final SpringContextDemoService service;
    private final ApplicationContext context;

    @Autowired
    Section12_ConditionalTest(
            SpringContextDemoService service,
            ApplicationContext context) {
        this.service = service;
        this.context = context;
    }

    @Test
    void shouldDemonstrateConditional() {
        ConditionalResult result = service.conditionalDemo();

        assertThat(result.activeBeans()).isNotEmpty();
        assertThat(result.conditionType()).isNotBlank();
    }

    @Test
    void shouldCreateBeanWhenPropertyTrue() {
        // По умолчанию feature.enabled отсутствует, но matchIfMissing=true
        assertThat(context.containsBean("enabledFeatureService")).isTrue();
    }

    @Test
    void shouldNotCreateDevToolsWithoutDevProfile() {
        assertThat(context.containsBean("devToolsService")).isFalse();
    }
}
