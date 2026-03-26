package com.example.springcontext;

import com.example.springcontext.entity.Product;
import com.example.springcontext.model.ApiModels.ScopeDemoResult;
import com.example.springcontext.service.ProductService;
import com.example.springcontext.service.PrototypeProductService;
import com.example.springcontext.service.SpringContextDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Раздел 3: Демонстрация Scope в Spring.
 * Scope определяет жизненный цикл и количество экземпляров бина.
 * По умолчанию все бины имеют scope="singleton".
 */
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section3_ScopeTest {

    private final SpringContextDemoService service;
    private final ApplicationContext context;
    private final ProductService productService;

    @Autowired
    Section3_ScopeTest(
            SpringContextDemoService service,
            ApplicationContext context,
            ProductService productService) {
        this.service = service;
        this.context = context;
        this.productService = productService;
    }

    @Test
    void shouldDemonstrateScopeDifference() {
        ScopeDemoResult result = service.scopeDemo();

        // Singleton - один и тот же экземпляр
        assertThat(result.singletonProduct1Id()).isNotNull();
        assertThat(result.singletonProduct2Id()).isNotNull();
        assertThat(result.singletonProduct1Id()).isEqualTo(result.singletonProduct2Id());

        // Prototype - разные экземпляры
        assertThat(result.prototypeProduct1Id()).isNotNull();
        assertThat(result.prototypeProduct2Id()).isNotNull();
        assertThat(result.prototypeProduct1Id()).isNotEqualTo(result.prototypeProduct2Id());
    }

    @Test
    void shouldReturnSameSingletonInstance() {
        // При многократном получении singleton бина возвращается один и тот же экземпляр
        Product product1 = productService.getDefaultProduct();
        Product product2 = productService.getDefaultProduct();

        assertThat(product1).isSameAs(product2);
        assertThat(product1.getId()).isEqualTo(product2.getId());
    }

    @Test
    void shouldReturnDifferentPrototypeInstances() {
        // При каждом запросе prototype бина создается новый экземпляр
        PrototypeProductService prototype1 = context.getBean(PrototypeProductService.class);
        PrototypeProductService prototype2 = context.getBean(PrototypeProductService.class);

        assertThat(prototype1).isNotSameAs(prototype2);
        assertThat(prototype1.getCurrentProduct().getId())
                .isNotEqualTo(prototype2.getCurrentProduct().getId());
    }

    @Test
    void shouldUseDefaultSingletonScope() {
        // По умолчанию бины имеют scope="singleton"
        String[] productServiceScopes = context.getBeanNamesForType(ProductService.class);
        assertThat(productServiceScopes).hasSize(1);
    }
}
