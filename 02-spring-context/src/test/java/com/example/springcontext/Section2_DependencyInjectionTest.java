package com.example.springcontext;

import com.example.springcontext.entity.Product;
import com.example.springcontext.model.ApiModels.DependencyInjectionResult;
import com.example.springcontext.service.CategoryService;
import com.example.springcontext.service.ProductService;
import com.example.springcontext.service.SpringContextDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Раздел 2: Демонстрация Dependency Injection (DI).
 * Внедрение зависимостей позволяет уменьшить связность кода
 * и упростить тестирование через инъекцию зависимостей.
 */
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section2_DependencyInjectionTest {

    private final SpringContextDemoService service;
    private final ApplicationContext context;
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    Section2_DependencyInjectionTest(
            SpringContextDemoService service,
            ApplicationContext context,
            ProductService productService,
            CategoryService categoryService) {
        this.service = service;
        this.context = context;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Test
    void shouldDemonstrateDependencyInjection() {
        DependencyInjectionResult result = service.dependencyInjectionDemo();

        // Конструкторная инъекция должна работать
        assertThat(result.constructorInjection()).isTrue();
        assertThat(result.setterInjection()).isTrue();
        assertThat(result.fieldInjection()).isTrue();
    }

    @Test
    void shouldInjectServicesViaConstructor() {
        // Проверка, что сервисы инъектированы через конструктор
        assertThat(productService).isNotNull();
        assertThat(categoryService).isNotNull();
    }

    @Test
    void shouldInjectBeansViaField() {
        // Проверка, что defaultProduct инъектирован через @Autowired на поле
        Product defaultProduct = productService.getDefaultProduct();
        assertThat(defaultProduct).isNotNull();
        assertThat(defaultProduct.getName()).isEqualTo("Default Product");
    }

    @Test
    void shouldInjectPropertiesViaSetter() {
        // Проверка, что свойства инъектированы через setter
        String defaultCategory = productService.getDefaultCategory();
        assertThat(defaultCategory).isNotNull();
        // Значение по умолчанию из @Value("${app.default-category:Uncategorized}")
        assertThat(defaultCategory).isEqualTo("Uncategorized");
    }

    @Test
    void shouldHaveCorrectBeanTypes() {
        // Проверка типов бинов
        assertThat(productService.getClass().getSimpleName()).isEqualTo("ProductService");
        assertThat(categoryService.getClass().getSimpleName()).isEqualTo("CategoryService");
    }
}
