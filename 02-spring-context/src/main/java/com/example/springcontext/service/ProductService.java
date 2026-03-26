package com.example.springcontext.service;

import com.example.springcontext.entity.Product;
import com.example.springcontext.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Сервис продуктов демонстрирует различные способы инъекции зависимостей.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private String defaultCategory;

    // Field injection (не рекомендуется, но демонстрируется)
    @Autowired
    @Qualifier("defaultProduct")
    private Product defaultProduct;

    // Конструкторная инъекция (рекомендуемый подход)
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Setter инъекция с @Value
    @Autowired
    public void setDefaultCategory(@Value("${app.default-category:Uncategorized}") String defaultCategory) {
        this.defaultCategory = defaultCategory;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByType(String type) {
        return productRepository.findByType(type);
    }

    public List<Product> findExpensive(BigDecimal threshold) {
        return productRepository.findByPriceGreaterThan(threshold);
    }

    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    public int count() {
        return productRepository.count();
    }

    public Product getDefaultProduct() {
        return defaultProduct;
    }

    public String getDefaultCategory() {
        return defaultCategory;
    }
}
