package com.example.springcontext.service;

import com.example.springcontext.entity.Category;
import com.example.springcontext.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис категорий демонстрирует использование @Service стереотипа.
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // Конструкторная инъекция
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> findById(String id) {
        return categoryRepository.findById(id);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }

    public void deleteById(String id) {
        categoryRepository.deleteById(id);
    }

    public int count() {
        return categoryRepository.count();
    }
}
