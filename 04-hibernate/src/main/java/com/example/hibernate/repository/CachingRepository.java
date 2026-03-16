package com.example.hibernate.repository;

import com.example.hibernate.entity.Product;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CachingRepository {

  private final EntityManager entityManager;

  public CachingRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional(readOnly = true)
  public Product findProduct(Long id) {
    return entityManager.find(Product.class, id);
  }

  @Transactional(readOnly = true)
  public List<Product> findProductsByTypeCached(String type) {
    return entityManager.createQuery(
            "select p from Product p where p.type = :type order by p.name", Product.class
        )
        .setParameter("type", type)
        .setHint("org.hibernate.cacheable", true)
        .getResultList();
  }

  @Transactional
  public Product updateName(Long id, String name) {
    Product product = entityManager.find(Product.class, id);
    product.setName(name);
    return product;
  }
}