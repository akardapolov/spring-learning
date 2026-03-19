package com.example.springdatajdbc;

import com.example.springdatajdbc.model.ApiModels.DifferenceItem;
import com.example.springdatajdbc.model.ApiModels.DifferencesResult;
import com.example.springdatajdbc.service.SpringDataJdbcDemoService;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Section1_DifferencesTest {

  private final SpringDataJdbcDemoService service =
      new SpringDataJdbcDemoService(null, null, null);

  @Test
  void shouldReturnExactJdbcVsJpaDifferences() {
    DifferencesResult expected = new DifferencesResult(List.of(
        new DifferenceItem("Persistence Context",
                           "No session or persistence context",
                           "EntityManager with persistence context"),
        new DifferenceItem("Lazy Loading",
                           "No lazy loading — all data loaded eagerly within aggregate",
                           "Lazy loading via proxies"),
        new DifferenceItem("Dirty Checking",
                           "No automatic dirty checking — must call save() explicitly",
                           "Automatic dirty checking on flush"),
        new DifferenceItem("Relations",
                           "Aggregate children + AggregateReference for cross-aggregate links",
                           "@OneToMany / @ManyToOne / @ManyToMany annotations"),
        new DifferenceItem("Saving",
                           "Entire aggregate saved/deleted atomically",
                           "Fine-grained cascade control per relation"),
        new DifferenceItem("Identity",
                           "New entity: id == null; existing: id != null",
                           "Managed by persistence context identity map"),
        new DifferenceItem("Schema",
                           "Explicit SQL schema required (schema.sql)",
                           "Hibernate ddl-auto can generate schema")
    ));

    assertThat(service.differencesDemo()).isEqualTo(expected);
  }
}