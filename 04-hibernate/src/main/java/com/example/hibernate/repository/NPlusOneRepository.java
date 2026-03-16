package com.example.hibernate.repository;

import com.example.hibernate.entity.Category;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class NPlusOneRepository {

  private final EntityManager entityManager;

  public NPlusOneRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional(readOnly = true)
  public List<Category> findAllLazy() {
    return entityManager.createQuery("select c from Category c", Category.class).getResultList();
  }

  @Transactional(readOnly = true)
  public List<Category> findAllJoinFetch() {
    return entityManager.createQuery(
        "select distinct c from Category c join fetch c.products", Category.class
    ).getResultList();
  }

  @Transactional(readOnly = true)
  public List<Category> findAllWithEntityGraph() {
    EntityGraph<?> graph = entityManager.getEntityGraph("category-with-products");
    return entityManager.createQuery("select c from Category c", Category.class)
        .setHint("jakarta.persistence.fetchgraph", graph)
        .getResultList();
  }

  @Transactional(readOnly = true)
  public List<Category> findAllSubselectAndBatch() {
    return entityManager.createQuery("select c from Category c", Category.class).getResultList();
  }
}