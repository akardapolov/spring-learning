package com.example.hibernate.repository;

import com.example.hibernate.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class QueryComparisonRepository {

  private final EntityManager entityManager;

  public QueryComparisonRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional(readOnly = true)
  public List<Product> nativeQueryByType(String type) {
    return entityManager.createNativeQuery(
        "select * from products where type = ? order by name",
        Product.class
    ).setParameter(1, type).getResultList();
  }

  @Transactional(readOnly = true)
  public List<Product> jpqlByType(String type) {
    return entityManager.createQuery(
        "select p from Product p where p.type = :type order by p.name",
        Product.class
    ).setParameter("type", type).getResultList();
  }

  @Transactional(readOnly = true)
  public List<Product> criteriaByType(String type) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
    Root<Product> root = criteriaQuery.from(Product.class);
    criteriaQuery.select(root)
        .where(criteriaBuilder.equal(root.get("type"), type))
        .orderBy(criteriaBuilder.asc(root.get("name")));
    return entityManager.createQuery(criteriaQuery).getResultList();
  }
}