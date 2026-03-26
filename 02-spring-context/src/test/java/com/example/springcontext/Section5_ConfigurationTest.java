package com.example.springcontext;

import com.example.springcontext.config.AppConfig;
import com.example.springcontext.entity.Category;
import com.example.springcontext.entity.Product;
import com.example.springcontext.model.ApiModels.ConfigurationResult;
import com.example.springcontext.service.SpringContextDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Раздел 5: Демонстрация конфигурации через @Configuration и @Bean.
 * Java-конфигурация позволяет декларативно определить бины и их зависимости.
 */
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section5_ConfigurationTest {

    private final SpringContextDemoService service;
    private final ApplicationContext context;
    private final AppConfig appConfig;

    @Autowired
    Section5_ConfigurationTest(
            SpringContextDemoService service,
            ApplicationContext context,
            AppConfig appConfig) {
        this.service = service;
        this.context = context;
        this.appConfig = appConfig;
    }

    @Test
    void shouldDemonstrateConfiguration() {
        ConfigurationResult result = service.configurationDemo();

        // Проверка, что конфигурация создала бины
        assertThat(result.configuredBeanName()).isEqualTo("smartphoneProduct");
        assertThat(result.allBeanNames()).isNotEmpty();
    }

    @Test
    void shouldCreateBeansViaConfiguration() {
        // Проверка, что бины созданы через @Configuration
        assertThat(context.containsBean("defaultProduct")).isTrue();
        assertThat(context.containsBean("electronicsCategory")).isTrue();
        assertThat(context.containsBean("smartphoneProduct")).isTrue();
    }

    @Test
    void shouldWiringDependenciesCorrectly() {
        // Проверка, что зависимости подключены правильно
        Product smartphone = context.getBean("smartphoneProduct", Product.class);
        assertThat(smartphone.getCategory()).isNotNull();
        assertThat(smartphone.getCategory().getTitle()).isEqualTo("Electronics");
    }

    @Test
    void shouldHavePrimaryBean() {
        // Проверка, что primary бин определен
        assertThat(context.containsBean("primaryProduct")).isTrue();
        Product primaryProduct = context.getBean(Product.class); // По умолчанию берется @Primary
        assertThat(primaryProduct).isNotNull();
        assertThat(primaryProduct.getName()).isEqualTo("Primary Product");
    }

    @Test
    void shouldInjectDependenciesInConfiguration() {
        // Проверка, что бины инъектируются в методы @Bean
        assertThat(context.containsBean("productService")).isTrue();
        assertThat(context.containsBean("categoryService")).isTrue();
    }
}
