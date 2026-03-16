package com.example.hibernate.repository;

import com.example.hibernate.entity.Category;
import com.example.hibernate.exception.CategoryNotFoundException;
import com.example.hibernate.exception.ProductNotFoundException;
import com.example.hibernate.entity.Product;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FetchingRepository {

  private final EntityManager entityManager;

  public FetchingRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional(readOnly = true)
  public Category findCategory(Long id) {
    Category category = entityManager.find(Category.class, id);
    if (category == null) {
      throw new CategoryNotFoundException(id);
    }
    return category;
  }

  @Transactional(readOnly = true)
  public Product findProduct(Long id) {
    Product product = entityManager.find(Product.class, id);
    if (product == null) {
      throw new ProductNotFoundException(id);
    }
    return product;
  }
}