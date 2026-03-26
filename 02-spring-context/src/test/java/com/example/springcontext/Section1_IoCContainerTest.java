package com.example.springcontext;

import com.example.springcontext.model.ApiModels.IocContainerResult;
import com.example.springcontext.service.SpringContextDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Раздел 1: Демонстрация IoC контейнера Spring.
 * Инверсия управления (Inversion of Control) - основной паттерн Spring,
 * позволяющий контейнеру управлять жизненным циклом и зависимостями объектов.
 */
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section1_IoCContainerTest {

    private final SpringContextDemoService service;
    private final ApplicationContext context;

    @Autowired
    Section1_IoCContainerTest(SpringContextDemoService service, ApplicationContext context) {
        this.service = service;
        this.context = context;
    }

    @Test
    void shouldDemonstrateIoCContainer() {
        IocContainerResult result = service.iocContainerDemo();

        // Контейнер должен быть инициализирован
        assertThat(result.containerType()).isNotBlank();

        // Должны быть зарегистрированы бины
        assertThat(result.beanNames()).isNotEmpty();

        // Бины ProductService и CategoryService должны быть в контексте
        assertThat(result.productBeanClass()).isEqualTo("ProductService");
        assertThat(result.categoryBeanClass()).isEqualTo("CategoryService");
    }

    @Test
    void shouldContainExpectedBeans() {
        // Проверка, что контейнер содержит ожидаемые бины
        assertThat(context.containsBean("productService")).isTrue();
        assertThat(context.containsBean("categoryService")).isTrue();
        assertThat(context.containsBean("orderService")).isTrue();
        assertThat(context.containsBean("productRepository")).isTrue();
        assertThat(context.containsBean("categoryRepository")).isTrue();
    }

    @Test
    void shouldRetrieveBeansFromContext() {
        // Получение бинов из контекста
        var productService = context.getBean("productService");
        var categoryRepository = context.getBean("categoryRepository");

        assertThat(productService).isNotNull();
        assertThat(categoryRepository).isNotNull();
    }
}
