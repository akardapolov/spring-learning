package com.example.springdatajpa;

import com.example.springdatajpa.model.ApiModels.SpecificationDemoResult;
import com.example.springdatajpa.service.SpringDataDemoService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section6_SpecificationTest {

  private final SpringDataDemoService service;

  Section6_SpecificationTest(SpringDataDemoService service) {
    this.service = service;
  }

  @Test
  void shouldFilterBySpecification() {
    SpecificationDemoResult result = service.specificationDemo("DEVICE", "pho", BigDecimal.valueOf(500));

    assertThat(result.matchedCount()).isGreaterThan(0);
    assertThat(result.items()).isNotEmpty();
    assertThat(result.items().getFirst().name().toLowerCase()).contains("pho");
  }
}