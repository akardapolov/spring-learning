package com.example.hibernate;

import com.example.hibernate.entity.Product;
import com.example.hibernate.springdata.ProductSpringDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section1_BasicMappingTest {

  private final ProductSpringDataRepository repository;

  Section1_BasicMappingTest(ProductSpringDataRepository repository) {
    this.repository = repository;
  }

  @Test
  void shouldLoadMappedEntity() {
    Product product = repository.findAll().stream().findFirst().orElseThrow();
    assertThat(product.getId()).isNotNull();
    assertThat(product.getName()).isNotBlank();
  }
}