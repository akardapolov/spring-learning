package com.example.springcontext.config;

import com.example.springcontext.entity.Category;
import com.example.springcontext.entity.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.math.BigDecimal;

/**
 * Конфигурация приложения через @Configuration и @Bean.
 * Демонстрирует декларативное определение бинов.
 *
 * <p>Примечание: ProductService и CategoryService здесь не определяются,
 * так как они уже зарегистрированы через @Service (см. стереотипы, Section 6).</p>
 */
@Configuration
public class AppConfig {

    /**
     * Бин продукта, созданный через @Bean.
     */
    @Bean(name = "defaultProduct")
    public Product defaultProduct() {
        return new Product("Default Product", "DEVICE", BigDecimal.valueOf(99.99));
    }

    /**
     * Бин категории для электроники.
     */
    @Bean(name = "electronicsCategory")
    public Category electronicsCategory() {
        return new Category("Electronics", "Electronic devices and accessories");
    }

    /**
     * Бин продукта для электроники, зависящий от категории.
     */
    @Bean(name = "smartphoneProduct")
    public Product smartphoneProduct(Category electronicsCategory) {
        Product product = new Product("Smartphone", "DEVICE", BigDecimal.valueOf(699.99));
        product.setCategory(electronicsCategory);
        return product;
    }

    /**
     * Primary бин продукта — выбирается по умолчанию при инъекции по типу.
     */
    @Bean
    @Primary
    public Product primaryProduct() {
        return new Product("Primary Product", "DEFAULT", BigDecimal.valueOf(1.00));
    }
}
