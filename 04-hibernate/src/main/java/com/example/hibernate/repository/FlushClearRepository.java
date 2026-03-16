package com.example.hibernate.repository;

import com.example.hibernate.entity.Product;
import com.example.hibernate.entity.ProductDetails;
import com.example.hibernate.model.ApiModels.FlushClearResult;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FlushClearRepository {

  private final EntityManager entityManager;

  public FlushClearRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional
  public FlushClearResult demonstrate() {
    Product product = new Product(
        "Flush Clear Demo",
        "DEMO",
        BigDecimal.valueOf(321),
        new ProductDetails("Demo Corp", "FLUSH-1")
    );

    entityManager.persist(product);
    Long idAfterPersist = product.getId();

    entityManager.flush();
    boolean containsAfterFlush = entityManager.contains(product);

    entityManager.clear();
    boolean containsAfterClear = entityManager.contains(product);

    Product reloaded = entityManager.find(Product.class, product.getId());

    return new FlushClearResult(
        idAfterPersist,
        containsAfterFlush,
        containsAfterClear,
        reloaded.getId(),
        product == reloaded
    );
  }
}