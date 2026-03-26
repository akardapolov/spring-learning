package com.example.springcontext.repository;

import com.example.springcontext.entity.Category;
import com.example.springcontext.entity.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Репозиторий продуктов для демонстрации @Repository стереотипа.
 * В отличие от модуля с JPA, это in-memory реализация.
 */
@Repository
public class ProductRepository {

    private final Map<String, Product> storage = new ConcurrentHashMap<>();

    public ProductRepository() {
        // Инициализация тестовыми данными
        initializeTestData();
    }

    private void initializeTestData() {
        Category electronics = new Category("Electronics", "Electronic devices");
        Category accessories = new Category("Accessories", "Device accessories");

        Product p1 = new Product("Smartphone", "DEVICE", BigDecimal.valueOf(699.99));
        p1.setCategory(electronics);
        p1.addTag("mobile");
        p1.addTag("5g");
        save(p1);

        Product p2 = new Product("Laptop", "DEVICE", BigDecimal.valueOf(1299.99));
        p2.setCategory(electronics);
        p2.addTag("computer");
        p2.addTag("portable");
        save(p2);

        Product p3 = new Product("Headphones", "ACCESSORY", BigDecimal.valueOf(149.99));
        p3.setCategory(accessories);
        p3.addTag("audio");
        save(p3);

        Product p4 = new Product("Charger", "ACCESSORY", BigDecimal.valueOf(29.99));
        p4.setCategory(accessories);
        p4.addTag("power");
        save(p4);
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            Product newProduct = new Product(product.getName(), product.getType(), product.getPrice());
            newProduct.setCategory(product.getCategory());
            newProduct.getTags().addAll(product.getTags());
            storage.put(newProduct.getId(), newProduct);
            return newProduct;
        }
        storage.put(product.getId(), product);
        return product;
    }

    public Optional<Product> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Product> findAll() {
        return new ArrayList<>(storage.values());
    }

    public List<Product> findByType(String type) {
        return storage.values().stream()
                .filter(p -> type.equals(p.getType()))
                .toList();
    }

    public List<Product> findByPriceGreaterThan(BigDecimal price) {
        return storage.values().stream()
                .filter(p -> p.getPrice().compareTo(price) > 0)
                .toList();
    }

    public void deleteById(String id) {
        storage.remove(id);
    }

    public int count() {
        return storage.size();
    }
}
