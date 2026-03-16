package com.example.hibernate.repository;

import com.example.hibernate.entity.Category;
import com.example.hibernate.exception.CategoryNotFoundException;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LazyExceptionRepository {

  private final EntityManager entityManager;

  public LazyExceptionRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional(readOnly = true)
  public Category loadCategory(Long id) {
    Category category = entityManager.find(Category.class, id);
    if (category == null) {
      throw new CategoryNotFoundException(id);
    }
    return category;
  }
}