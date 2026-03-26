package com.example.springcontext;

import com.example.springcontext.model.ApiModels.StereotypesResult;
import com.example.springcontext.repository.CategoryRepository;
import com.example.springcontext.repository.ProductRepository;
import com.example.springcontext.service.CategoryService;
import com.example.springcontext.service.OrderService;
import com.example.springcontext.service.ProductService;
import com.example.springcontext.service.SpringContextDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Раздел 6: Демонстрация стереотипных аннотаций Spring.
 * @Component, @Service, @Repository, @Controller - специализированные
 * варианты @Component с семантическим значением.
 */
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section6_StereotypesTest {

    private final SpringContextDemoService service;
    private final ApplicationContext context;

    @Autowired
    Section6_StereotypesTest(
            SpringContextDemoService service,
            ApplicationContext context) {
        this.service = service;
        this.context = context;
    }

    @Test
    void shouldDemonstrateStereotypes() {
        StereotypesResult result = service.stereotypesDemo();

        // Проверка, что бины с различными стереотипами созданы
        assertThat(result.productServiceType()).isEqualTo("ProductService");
        assertThat(result.categoryRepositoryType()).isEqualTo("CategoryRepository");
        assertThat(result.orderServiceType()).isEqualTo("OrderService");

        // Должны быть компоненты с @Component, @Service, @Repository
        assertThat(result.allComponentNames()).isNotEmpty();
    }

    @Test
    void shouldCreateServiceBeans() {
        // @Service бины
        assertThat(context.containsBean("productService")).isTrue();
        assertThat(context.containsBean("categoryService")).isTrue();
        assertThat(context.containsBean("orderService")).isTrue();
    }

    @Test
    void shouldCreateRepositoryBeans() {
        // @Repository бины
        assertThat(context.containsBean("productRepository")).isTrue();
        assertThat(context.containsBean("categoryRepository")).isTrue();
    }

    @Test
    void shouldCreateComponentBeans() {
        // @Component бины
        assertThat(context.containsBean("eventPublisher")).isTrue();
        assertThat(context.containsBean("customEventListener")).isTrue();
        assertThat(context.containsBean("contextAwareBean")).isTrue();
    }

    @Test
    void shouldHaveCorrectTypes() {
        // Проверка типов бинов
        var productService = context.getBean(ProductService.class);
        var categoryService = context.getBean(CategoryService.class);
        var orderService = context.getBean(OrderService.class);
        var productRepository = context.getBean(ProductRepository.class);
        var categoryRepository = context.getBean(CategoryRepository.class);

        assertThat(productService).isNotNull();
        assertThat(categoryService).isNotNull();
        assertThat(orderService).isNotNull();
        assertThat(productRepository).isNotNull();
        assertThat(categoryRepository).isNotNull();
    }
}
