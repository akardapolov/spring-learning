package com.example.hibernate.service;

import com.example.hibernate.entity.Product;
import com.example.hibernate.model.ApiModels.LifecycleResult;
import com.example.hibernate.repository.EntityLifecycleRepository;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntityLifecycleDemoService {

  private final EntityLifecycleRepository repository;

  public EntityLifecycleDemoService(EntityLifecycleRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public LifecycleResult demonstrateLifecycle() {
    Product transientProduct = new Product("Lifecycle Product", "DEMO", BigDecimal.valueOf(10));
    boolean transientHasId = transientProduct.getId() != null;

    repository.persist(transientProduct);
    boolean managedAfterPersistHasId = transientProduct.getId() != null;

    Product detached = repository.detachAndReturn(transientProduct.getId());
    Long detachedId = detached.getId();

    detached.setPrice(BigDecimal.valueOf(15));
    Product merged = repository.merge(detached);

    return new LifecycleResult(
        transientHasId,
        managedAfterPersistHasId,
        detachedId,
        merged.getId(),
        merged.getPrice()
    );
  }
}