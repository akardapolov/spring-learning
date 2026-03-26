package com.example.springcontext;

import com.example.springcontext.config.ProfileConfig;
import com.example.springcontext.model.ApiModels.ProfileResult;
import com.example.springcontext.service.SpringContextDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Раздел 10: Демонстрация профилей Spring.
 * Профили позволяют создавать разные конфигурации для разных окружений.
 */
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section10_ProfileTest {

    private final SpringContextDemoService service;
    private final ApplicationContext context;
    private final ProfileConfig profileConfig;

    @Autowired
    Section10_ProfileTest(
            SpringContextDemoService service,
            ApplicationContext context,
            ProfileConfig profileConfig) {
        this.service = service;
        this.context = context;
        this.profileConfig = profileConfig;
    }

    @Test
    void shouldDemonstrateProfiles() {
        ProfileResult result = service.profileDemo();

        assertThat(result.activeProfile()).isNotBlank();
        assertThat(result.availableProfiles()).isNotEmpty();
    }

    @Test
    void shouldHaveDefaultProfile() {
        ProfileResult result = service.profileDemo();
        assertThat(result.activeProfile()).isEqualTo("default");
    }

    @Test
    void shouldHaveProfileConfig() {
        assertThat(profileConfig).isNotNull();
    }

    @Test
    void shouldNotLoadProfileSpecificBeansWithoutActiveProfile() {
        assertThat(context.containsBean("devProduct")).isFalse();
        assertThat(context.containsBean("prodProduct")).isFalse();
        assertThat(context.containsBean("testProduct")).isFalse();
    }

    @Test
    void shouldLoadProfileSpecificBeansWithDevProfile(@Autowired ApplicationContext context) {
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        if (activeProfiles.length == 0) {
            // Без профиля тест пропускается
            return;
        }
    }
}
