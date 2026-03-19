package com.example.springdatajdbc;

import com.example.springdatajdbc.model.ApiModels.GlossaryItem;
import com.example.springdatajdbc.model.ApiModels.GlossaryResult;
import com.example.springdatajdbc.service.SpringDataJdbcDemoService;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Section6_GlossaryTest {

  private final SpringDataJdbcDemoService service =
      new SpringDataJdbcDemoService(null, null, null);

  @Test
  void shouldReturnExactGlossary() {
    GlossaryResult expected = new GlossaryResult(List.of(
        new GlossaryItem("Aggregate",
                         "A cluster of domain objects treated as a single unit for data changes."),
        new GlossaryItem("Aggregate Root",
                         "The entry point entity of an aggregate. Only root has a Repository."),
        new GlossaryItem("Entity",
                         "An object with identity (id field)."),
        new GlossaryItem("Value Object",
                         "An object without identity, embedded into entity (e.g. ProductDetails)."),
        new GlossaryItem("AggregateReference",
                         "A typed ID reference to another aggregate root, replacing JPA associations."),
        new GlossaryItem("@MappedCollection",
                         "Annotation declaring a one-to-many child collection inside an aggregate."),
        new GlossaryItem("@Embedded",
                         "Annotation for embedding a value object's columns into the parent table."),
        new GlossaryItem("@Version",
                         "Optimistic locking field incremented on each save."),
        new GlossaryItem("Lifecycle Events",
                         "BeforeConvert, BeforeSave, AfterSave, AfterConvert — hooks during persistence operations."),
        new GlossaryItem("Repository",
                         "Interface providing CRUD operations for an aggregate root.")
    ));

    assertThat(service.glossaryDemo()).isEqualTo(expected);
  }
}