package com.example.hibernate.repository;

import com.example.hibernate.entity.Product;
import com.example.hibernate.entity.ProductDetails;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BatchInsertRepository {

  private final EntityManager entityManager;

  public BatchInsertRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional
  public int insertProducts(int count) {
    for (int i = 0; i < count; i++) {
      Product product = new Product(
          "Batch Product " + i,
          "BATCH",
          BigDecimal.valueOf(100 + i),
          new ProductDetails("Batch Inc", "BATCH-" + i)
      );
      entityManager.persist(product);

      if (i > 0 && i % 20 == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    return count;
  }
}