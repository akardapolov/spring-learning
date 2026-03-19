package com.example.springdatajdbc;

import com.example.springdatajdbc.model.ApiModels.CustomQueryResult;
import com.example.springdatajdbc.service.SpringDataJdbcDemoService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section4_CustomQueriesTest {

  private final SpringDataJdbcDemoService service;

  Section4_CustomQueriesTest(SpringDataJdbcDemoService service) {
    this.service = service;
  }

  @Test
  void shouldFindCategoriesByTitlePart() {
    CustomQueryResult result = service.customQueryDemo("oo");

    assertThat(result.count()).isGreaterThan(0);
    assertThat(result.categoryTitles()).contains("Books");
  }

  @Test
  void shouldFindCategoriesWithExpensiveProducts() {
    CustomQueryResult result = service.customQueryByMinPrice(BigDecimal.valueOf(1000));

    assertThat(result.count()).isGreaterThan(0);
    assertThat(result.categoryTitles()).contains("Electronics");
  }
}
