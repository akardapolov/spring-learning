package com.example.springcontext.repository;

import com.example.springcontext.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Репозиторий категорий для демонстрации @Repository стереотипа.
 * В отличие от модуля с JPA, это in-memory реализация.
 */
@Repository
public class CategoryRepository {

    private final Map<String, Category> storage = new ConcurrentHashMap<>();

    public CategoryRepository() {
        // Инициализация тестовыми данными
        initializeTestData();
    }

    private void initializeTestData() {
        save(new Category("Electronics", "Electronic devices and accessories"));
        save(new Category("Clothing", "Apparel and fashion items"));
        save(new Category("Home", "Home and kitchen products"));
    }

    public Category save(Category category) {
        if (category.getId() == null) {
            Category newCategory = new Category(category.getTitle(), category.getDescription());
            storage.put(newCategory.getId(), newCategory);
            return newCategory;
        }
        storage.put(category.getId(), category);
        return category;
    }

    public Optional<Category> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Category> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<Category> findByTitle(String title) {
        return storage.values().stream()
                .filter(c -> title.equals(c.getTitle()))
                .findFirst();
    }

    public void deleteById(String id) {
        storage.remove(id);
    }

    public int count() {
        return storage.size();
    }
}
