package com.example.springdatajdbc;

import com.example.springdatajdbc.model.ApiModels.CategoryDto;
import com.example.springdatajdbc.repository.CategoryRepository;
import com.example.springdatajdbc.service.SpringDataJdbcDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section3_MappingAndRelationsTest {

  private final SpringDataJdbcDemoService service;
  private final CategoryRepository categoryRepository;

  Section3_MappingAndRelationsTest(SpringDataJdbcDemoService service,
                                   CategoryRepository categoryRepository) {
    this.service = service;
    this.categoryRepository = categoryRepository;
  }

  @Test
  void shouldLoadAggregateWithChildrenAndEmbedded() {
    Long id = categoryRepository.findByTitle("Electronics").orElseThrow().getId();

    CategoryDto dto = service.getCategory(id);

    assertThat(dto.title()).isEqualTo("Electronics");
    assertThat(dto.products()).hasSize(2);
    assertThat(dto.products().stream().anyMatch(p -> p.manufacturer() != null)).isTrue();
  }

  @Test
  void shouldHaveAggregateReferenceToTag() {
    Long id = categoryRepository.findByTitle("Electronics").orElseThrow().getId();

    CategoryDto dto = service.getCategory(id);

    assertThat(dto.products().stream().allMatch(p -> p.tagId() != null)).isTrue();
  }
}
