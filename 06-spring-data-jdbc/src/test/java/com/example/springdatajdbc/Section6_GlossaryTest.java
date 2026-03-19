package com.example.springdatajdbc;

import com.example.springdatajdbc.model.ApiModels.GlossaryResult;
import com.example.springdatajdbc.service.SpringDataJdbcDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section6_GlossaryTest {

  private final SpringDataJdbcDemoService service;

  Section6_GlossaryTest(SpringDataJdbcDemoService service) {
    this.service = service;
  }

  @Test
  void shouldReturnGlossaryTerms() {
    GlossaryResult result = service.glossaryDemo();

    assertThat(result.items()).hasSizeGreaterThanOrEqualTo(6);
    assertThat(result.items().stream().anyMatch(i -> i.term().equals("Aggregate"))).isTrue();
    assertThat(result.items().stream().anyMatch(i -> i.term().equals("AggregateReference"))).isTrue();
  }
}
