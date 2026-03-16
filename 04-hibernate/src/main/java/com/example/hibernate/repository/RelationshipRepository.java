package com.example.hibernate.repository;

import com.example.hibernate.entity.Category;
import com.example.hibernate.entity.Customer;
import com.example.hibernate.entity.CustomerOrder;
import com.example.hibernate.entity.Product;
import com.example.hibernate.entity.Tag;
import jakarta.persistence.EntityManager;
import java.util.SequencedCollection;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RelationshipRepository {

  private final EntityManager entityManager;

  public RelationshipRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional(readOnly = true)
  public SequencedCollection<Category> findAllCategories() {
    return entityManager.createQuery("select c from Category c", Category.class).getResultList();
  }

  @Transactional(readOnly = true)
  public SequencedCollection<Tag> findAllTags() {
    return entityManager.createQuery("select t from Tag t", Tag.class).getResultList();
  }

  @Transactional(readOnly = true)
  public SequencedCollection<Customer> findAllCustomers() {
    return entityManager.createQuery("select c from Customer c", Customer.class).getResultList();
  }

  @Transactional(readOnly = true)
  public Product findAnyProduct() {
    return entityManager.createQuery("select p from Product p", Product.class)
        .setMaxResults(1)
        .getSingleResult();
  }

  @Transactional(readOnly = true)
  public CustomerOrder findAnyOrder() {
    return entityManager.createQuery("select o from CustomerOrder o", CustomerOrder.class)
        .setMaxResults(1)
        .getSingleResult();
  }
}