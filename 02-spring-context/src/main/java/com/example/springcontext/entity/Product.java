package com.example.springcontext.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Модель продукта для демонстрации работы Spring IoC контейнера.
 * Не содержит JPA аннотаций, так как модуль 02-spring-context фокусируется на Core/IoC.
 */
public class Product {

    private final String id;
    private String name;
    private String type;
    private BigDecimal price;
    private Category category;
    private final Set<String> tags = new HashSet<>();

    public Product() {
        this.id = UUID.randomUUID().toString();
    }

    public Product(String id, String name, String type, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public Product(String name, String type, BigDecimal price) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", category=" + (category != null ? category.getTitle() : "null") +
                ", tags=" + tags +
                '}';
    }
}
