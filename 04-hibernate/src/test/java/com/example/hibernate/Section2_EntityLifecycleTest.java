package com.example.hibernate;

import com.example.hibernate.entity.Product;
import com.example.hibernate.repository.EntityLifecycleRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section2_EntityLifecycleTest {

  private final EntityLifecycleRepository repository;

  Section2_EntityLifecycleTest(EntityLifecycleRepository repository) {
    this.repository = repository;
  }

  @Test
  void shouldPersistEntityAndAssignId() {
    Product product = new Product("Persist Test", "DEMO", BigDecimal.TEN);

    repository.persist(product);

    assertThat(product.getId()).isNotNull();

    Product reloaded = repository.find(product.getId());
    assertThat(reloaded).isNotNull();
    assertThat(reloaded.getName()).isEqualTo("Persist Test");
    assertThat(reloaded.getPrice()).isEqualByComparingTo("10");
    assertThat(reloaded.getType()).isEqualTo("DEMO");
  }

  @Test
  void detachedChangesShouldNotBeSavedWithoutMerge() {
    Product product = new Product("Detached Test", "DEMO", BigDecimal.TEN);
    repository.persist(product);

    Product detached = repository.detachAndReturn(product.getId());
    detached.setPrice(BigDecimal.valueOf(50));

    Product reloaded = repository.find(product.getId());

    assertThat(reloaded.getPrice()).isEqualByComparingTo("10");
  }

  @Test
  void shouldPersistDetachAndMergeEntity() {
    Product product = new Product("Lifecycle Test", "DEMO", BigDecimal.TEN);
    repository.persist(product);

    assertThat(product.getId()).isNotNull();

    Product detached = repository.detachAndReturn(product.getId());
    detached.setPrice(BigDecimal.valueOf(25));

    Product merged = repository.merge(detached);

    assertThat(merged.getId()).isEqualTo(product.getId());
    assertThat(merged.getPrice()).isEqualByComparingTo("25");
    assertThat(merged).isNotSameAs(detached);

    Product reloaded = repository.find(product.getId());
    assertThat(reloaded.getPrice()).isEqualByComparingTo("25");
  }

  @Test
  void shouldRemoveEntity() {
    Product product = new Product("Remove Test", "DEMO", BigDecimal.ONE);
    repository.persist(product);

    Long id = product.getId();
    assertThat(repository.find(id)).isNotNull();

    repository.remove(id);

    Product deleted = repository.find(id);
    assertThat(deleted).isNull();
  }
}