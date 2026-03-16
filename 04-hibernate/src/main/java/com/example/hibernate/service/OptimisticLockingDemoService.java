package com.example.hibernate.service;

import com.example.hibernate.entity.Product;
import com.example.hibernate.exception.ProductNotFoundException;
import com.example.hibernate.model.ApiModels.OptimisticLockingResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.RollbackException;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptimisticLockingDemoService {

  private final EntityManagerFactory entityManagerFactory;

  public OptimisticLockingDemoService(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  public OptimisticLockingResult demonstrate(Long productId) {
    EntityManager entityManager1 = entityManagerFactory.createEntityManager();
    EntityManager entityManager2 = entityManagerFactory.createEntityManager();

    EntityTransaction transaction1 = entityManager1.getTransaction();
    EntityTransaction transaction2 = entityManager2.getTransaction();

    Long initialVersionTx1 = null;
    Long initialVersionTx2 = null;
    boolean tx1Committed = false;
    boolean tx2Committed = false;
    boolean optimisticLockTriggered = false;
    String error = null;

    try {
      transaction1.begin();
      transaction2.begin();

      Product product1 = entityManager1.find(Product.class, productId);
      Product product2 = entityManager2.find(Product.class, productId);

      if (product1 == null || product2 == null) {
        throw new ProductNotFoundException(productId);
      }

      initialVersionTx1 = product1.getVersion();
      initialVersionTx2 = product2.getVersion();

      product1.setPrice(product1.getPrice().add(BigDecimal.ONE));
      transaction1.commit();
      tx1Committed = true;

      product2.setPrice(product2.getPrice().add(BigDecimal.TEN));

      try {
        transaction2.commit();
        tx2Committed = true;
      } catch (OptimisticLockException ex) {
        optimisticLockTriggered = true;
        error = ex.getMessage();
        if (transaction2.isActive()) {
          transaction2.rollback();
        }
      } catch (RollbackException ex) {
        optimisticLockTriggered = containsOptimisticLock(ex);
        error = ex.getMessage();
        if (transaction2.isActive()) {
          transaction2.rollback();
        }
      }

      return new OptimisticLockingResult(
          productId,
          initialVersionTx1,
          initialVersionTx2,
          tx1Committed,
          tx2Committed,
          optimisticLockTriggered,
          error
      );
    } finally {
      if (transaction1.isActive()) {
        transaction1.rollback();
      }
      if (transaction2.isActive()) {
        transaction2.rollback();
      }
      entityManager1.close();
      entityManager2.close();
    }
  }

  private boolean containsOptimisticLock(Throwable ex) {
    Throwable current = ex;
    while (current != null) {
      if (current instanceof OptimisticLockException) {
        return true;
      }
      current = current.getCause();
    }
    return false;
  }
}