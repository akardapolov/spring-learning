package com.example.springcontext;

import com.example.springcontext.listener.ContextAwareBean;
import com.example.springcontext.model.ApiModels.ContextAwareResult;
import com.example.springcontext.service.SpringContextDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Раздел 9: Демонстрация ApplicationContextAware.
 * ApplicationContextAware позволяет бину получить доступ к ApplicationContext.
 */
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section9_ContextAwareTest {

    private final SpringContextDemoService service;
    private final ApplicationContext context;
    private final ContextAwareBean contextAwareBean;

    @Autowired
    Section9_ContextAwareTest(
            SpringContextDemoService service,
            ApplicationContext context,
            ContextAwareBean contextAwareBean) {
        this.service = service;
        this.context = context;
        this.contextAwareBean = contextAwareBean;
    }

    @Test
    void shouldDemonstrateContextAware() {
        ContextAwareResult result = service.contextAwareDemo();

        // Проверка, что ApplicationContext доступен
        assertThat(result.applicationContextClass()).isNotBlank();
        assertThat(result.beanCount()).isGreaterThan(0);
        assertThat(result.containsProductService()).isTrue();
    }

    @Test
    void shouldHaveApplicationContextInjected() {
        // Проверка, что ApplicationContext инъектирован
        ApplicationContext injectedContext = contextAwareBean.getApplicationContext();
        assertThat(injectedContext).isNotNull();
        assertThat(injectedContext).isSameAs(context);
    }

    @Test
    void shouldGetBeanCount() {
        // Получение количества бинов
        int beanCount = contextAwareBean.getBeanCount();
        assertThat(beanCount).isGreaterThan(0);
    }

    @Test
    void shouldCheckBeanExistence() {
        // Проверка существования бина
        boolean containsProductService = contextAwareBean.containsProductService();
        assertThat(containsProductService).isTrue();
    }

    @Test
    void shouldGetApplicationContextClassName() {
        // Получение имени класса контекста
        String contextClassName = contextAwareBean.getApplicationContextClassName();
        assertThat(contextClassName).isNotBlank();
        assertThat(contextClassName).containsIgnoringCase("AnnotationConfig");
    }
}
