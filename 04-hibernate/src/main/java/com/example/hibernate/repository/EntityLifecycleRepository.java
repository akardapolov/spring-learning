package com.example.hibernate.repository;

import com.example.hibernate.entity.Product;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EntityLifecycleRepository {

  private final EntityManager entityManager;

  public EntityLifecycleRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional
  public Product persist(Product product) {
    entityManager.persist(product);
    return product;
  }

  @Transactional(readOnly = true)
  public Product find(Long id) {
    return entityManager.find(Product.class, id);
  }

  @Transactional
  public Product detachAndReturn(Long id) {
    Product product = entityManager.find(Product.class, id);
    entityManager.detach(product);
    return product;
  }

  @Transactional
  public Product merge(Product detached) {
    return entityManager.merge(detached);
  }

  @Transactional
  public void remove(Long id) {
    Product product = entityManager.find(Product.class, id);
    entityManager.remove(product);
  }

  @Transactional(readOnly = true)
  public boolean contains(Product product) {
    return entityManager.contains(product);
  }
}