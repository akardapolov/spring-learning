package com.example.hibernate;

import com.example.hibernate.repository.FetchingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section4_FetchingStrategiesTest {

  private final FetchingRepository repository;

  Section4_FetchingStrategiesTest(FetchingRepository repository) {
    this.repository = repository;
  }

  @Test
  @Transactional
  void shouldLoadLazyCollectionInsideTransaction() {
    var category = repository.findCategory(1L);
    assertThat(category.getProducts().size()).isGreaterThan(0);
  }

  @Test
  @Transactional
  void shouldLoadLazyManyToOneInsideTransaction() {
    var product = repository.findProduct(1L);
    assertThat(product.getCategory().getTitle()).isNotBlank();
  }
}