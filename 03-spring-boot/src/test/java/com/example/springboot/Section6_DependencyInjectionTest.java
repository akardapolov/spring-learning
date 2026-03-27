package com.example.springboot;

import com.example.springboot.service.AppInfoService;
import com.example.springboot.service.BeanService;
import com.example.springboot.service.ConditionalBeanService;
import com.example.springboot.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for Dependency Injection patterns.
 *
 * Demonstrates:
 * - Constructor injection (preferred)
 * - @Autowired annotation
 * - ApplicationContext injection
 * - @Qualifier for bean selection
 */
@SpringBootTest
class Section6_DependencyInjectionTest {

    // Constructor injection - preferred approach
    private final AppInfoService appInfoService;

    // Field injection via @Autowired (shown for completeness)
    @Autowired
    private ProfileService profileService;

    // ApplicationContext injection
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    public Section6_DependencyInjectionTest(AppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }

    @Test
    void constructorInjectionWorks() {
        assertThat(appInfoService).isNotNull();
        assertThat(appInfoService.getAppInfo()).isNotNull();
    }

    @Test
    void fieldInjectionWorks() {
        assertThat(profileService).isNotNull();
        assertThat(profileService.getCurrentProfile()).isNotNull();
    }

    @Test
    void applicationContextIsAvailable() {
        assertThat(applicationContext).isNotNull();
        assertThat(applicationContext.getBeanDefinitionNames()).isNotEmpty();
    }

    @Test
    void beanCanBeRetrievedFromContext() {
        BeanService beanService = applicationContext.getBean(BeanService.class);
        assertThat(beanService).isNotNull();
        assertThat(beanService.getBeanCount()).isGreaterThan(0);
    }

    @Test
    void sameBeanInstanceForSingleton() {
        // Singleton beans should return same instance
        BeanService bean1 = applicationContext.getBean(BeanService.class);
        BeanService bean2 = applicationContext.getBean(BeanService.class);
        assertThat(bean1).isSameAs(bean2);
    }

    @Test
    void serviceBeansAreProperlyInjected() {
        // Verify all services are properly injected
        assertThat(appInfoService).isNotNull();
        assertThat(profileService).isNotNull();

        ConditionalBeanService conditionalBeanService = applicationContext.getBean(ConditionalBeanService.class);
        assertThat(conditionalBeanService).isNotNull();
    }

    @Test
    void appInfoServiceCanGetGreeting() {
        String greeting = appInfoService.getGreetingMessage();
        assertThat(greeting).isNotNull();
        assertThat(greeting).isNotBlank();
    }
}
