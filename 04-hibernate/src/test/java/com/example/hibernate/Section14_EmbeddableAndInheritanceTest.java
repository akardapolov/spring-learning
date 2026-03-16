package com.example.hibernate;

import com.example.hibernate.entity.CardPaymentSingleTable;
import com.example.hibernate.entity.CustomerJoined;
import com.example.hibernate.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section14_EmbeddableAndInheritanceTest {

  @PersistenceContext
  private EntityManager entityManager;

  @Test
  @Transactional
  void shouldLoadEmbeddable() {
    Product product = entityManager.createQuery("select p from Product p", Product.class)
        .setMaxResults(1)
        .getSingleResult();

    assertThat(product.getDetails()).isNotNull();
    assertThat(product.getDetails().getManufacturer()).isNotBlank();
  }

  @Test
  @Transactional
  void shouldLoadSingleTableInheritance() {
    var payments = entityManager.createQuery("select p from PaymentSingleTable p", Object.class).getResultList();
    assertThat(payments).isNotEmpty();
    assertThat(payments.stream().anyMatch(CardPaymentSingleTable.class::isInstance)).isTrue();
  }

  @Test
  @Transactional
  void shouldLoadJoinedInheritance() {
    var persons = entityManager.createQuery("select p from PersonJoined p", Object.class).getResultList();
    assertThat(persons).isNotEmpty();
    assertThat(persons.stream().anyMatch(CustomerJoined.class::isInstance)).isTrue();
  }
}