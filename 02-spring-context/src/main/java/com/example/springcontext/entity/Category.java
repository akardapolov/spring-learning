package com.example.springcontext.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Модель категории для демонстрации работы Spring IoC контейнера.
 */
public class Category {

    private final String id;
    private String title;
    private String description;
    private final List<Product> products = new ArrayList<>();

    public Category() {
        this.id = UUID.randomUUID().toString();
    }

    public Category(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Category(String title, String description) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setCategory(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
