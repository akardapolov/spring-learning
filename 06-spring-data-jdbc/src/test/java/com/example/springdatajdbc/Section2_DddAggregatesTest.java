package com.example.springdatajdbc;

import com.example.springdatajdbc.model.ApiModels.AggregateSummary;
import com.example.springdatajdbc.model.ApiModels.DddResult;
import com.example.springdatajdbc.service.SpringDataJdbcDemoService;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Section2_DddAggregatesTest {

  private final SpringDataJdbcDemoService service =
      new SpringDataJdbcDemoService(null, null, null);

  @Test
  void shouldReturnExactAggregateDefinitions() {
    DddResult expected = new DddResult(List.of(
        new AggregateSummary("Category",
                             "Aggregate root owning CategoryProduct children. Products are saved/deleted together with Category.",
                             List.of("CategoryProduct")),
        new AggregateSummary("Customer",
                             "Aggregate root owning CustomerOrder children. Orders belong to Customer.",
                             List.of("CustomerOrder")),
        new AggregateSummary("Tag",
                             "Standalone aggregate. Referenced by CategoryProduct via AggregateReference<Tag, Long>.",
                             List.of())
    ));

    assertThat(service.dddDemo()).isEqualTo(expected);
  }
}