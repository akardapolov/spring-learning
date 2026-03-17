package com.example.springdatajpa;

import com.example.springdatajpa.model.ApiModels.RepositoryHierarchyResult;
import com.example.springdatajpa.service.SpringDataDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section1_RepositoryHierarchyTest {

  private final SpringDataDemoService service;

  Section1_RepositoryHierarchyTest(SpringDataDemoService service) {
    this.service = service;
  }

  @Test
  void shouldShowRepositoryHierarchy() {
    RepositoryHierarchyResult result = service.repositoryHierarchyDemo();

    assertThat(result.repositoryInterface()).isEqualTo("ProductRepository");
    assertThat(result.jpaRepositoryInterface()).isEqualTo("JpaRepository");
    assertThat(result.listPagingAndSortingRepositoryInterface()).isEqualTo("ListPagingAndSortingRepository");
    assertThat(result.listCrudRepositoryInterface()).isEqualTo("ListCrudRepository");
    assertThat(result.rootRepositoryInterface()).isEqualTo("Repository");
    assertThat(result.hierarchy()).contains("Repository", "CrudRepository", "JpaRepository");
  }
}