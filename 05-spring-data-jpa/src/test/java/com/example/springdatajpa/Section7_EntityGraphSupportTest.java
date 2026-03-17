package com.example.springdatajpa;

import com.example.springdatajpa.entity.Category;
import com.example.springdatajpa.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section7_EntityGraphSupportTest {

  private final CategoryRepository categoryRepository;

  Section7_EntityGraphSupportTest(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Test
  @Transactional
  void shouldLoadCategoriesWithProducts() {
    List<Category> categories = categoryRepository.findAllWithProducts();

    assertThat(categories).isNotEmpty();
    assertThat(categories.getFirst().getProducts()).isNotEmpty();
  }

  @Test
  @Transactional
  void shouldLoadDetailedCategoryByTitle() {
    Category category = categoryRepository.findDetailedByTitle("Electronics").orElseThrow();

    assertThat(category.getTitle()).isEqualTo("Electronics");
    assertThat(category.getProducts()).isNotEmpty();
  }
}