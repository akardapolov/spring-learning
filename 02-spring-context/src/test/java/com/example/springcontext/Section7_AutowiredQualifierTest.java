package com.example.springcontext;

import com.example.springcontext.entity.Product;
import com.example.springcontext.model.ApiModels.QualifierResult;
import com.example.springcontext.service.ProductService;
import com.example.springcontext.service.SpringContextDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Раздел 7: Демонстрация @Autowired с @Qualifier.
 * @Qualifier позволяет выбрать конкретный бин когда есть несколько кандидатов.
 */
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section7_AutowiredQualifierTest {

    private final SpringContextDemoService service;
    private final ApplicationContext context;

    Section7_AutowiredQualifierTest(
            SpringContextDemoService service,
            ApplicationContext context) {
        this.service = service;
        this.context = context;
    }

    @Test
    void shouldDemonstrateQualifier() {
        QualifierResult result = service.qualifierDemo();

        // Проверка, что primary сервис выбран корректно
        assertThat(result.primaryServiceName()).isNotBlank();
        assertThat(result.secondaryServiceName()).isNotBlank();
        assertThat(result.selectedServiceName()).isEqualTo("ProductService");
    }

    @Test
    void shouldInjectUsingQualifier() {
        // Проверка, что бин с @Qualifier инъектирован
        var productService = context.getBean(ProductService.class);
        Product defaultProduct = productService.getDefaultProduct();

        assertThat(defaultProduct).isNotNull();
        assertThat(defaultProduct.getName()).isEqualTo("Default Product");
    }

    @Test
    void shouldUsePrimaryBean() {
        // При наличии @Primary бина он выбирается по умолчанию
        Product primaryProduct = context.getBean(Product.class);

        assertThat(primaryProduct).isNotNull();
        assertThat(primaryProduct.getName()).isEqualTo("Primary Product");
    }

    @Test
    void shouldGetSpecificBeanByName() {
        // Получение бина по имени (альтернатива @Qualifier)
        Product defaultProduct = context.getBean("defaultProduct", Product.class);
        Product smartphoneProduct = context.getBean("smartphoneProduct", Product.class);

        assertThat(defaultProduct).isNotNull();
        assertThat(defaultProduct.getName()).isEqualTo("Default Product");
        assertThat(smartphoneProduct).isNotNull();
        assertThat(smartphoneProduct.getName()).isEqualTo("Smartphone");
    }
}
