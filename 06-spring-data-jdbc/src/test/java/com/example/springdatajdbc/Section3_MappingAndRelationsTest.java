package com.example.springdatajdbc;

import com.example.springdatajdbc.config.LifecycleEventsCollector;
import com.example.springdatajdbc.entity.Category;
import com.example.springdatajdbc.entity.CategoryProduct;
import com.example.springdatajdbc.entity.ProductDetails;
import com.example.springdatajdbc.entity.Tag;
import com.example.springdatajdbc.model.ApiModels.CategoryDto;
import com.example.springdatajdbc.model.ApiModels.ProductDto;
import com.example.springdatajdbc.repository.CategoryRepository;
import com.example.springdatajdbc.repository.TagRepository;
import com.example.springdatajdbc.service.SpringDataJdbcDemoService;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import({SpringDataJdbcDemoService.class, LifecycleEventsCollector.class})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section3_MappingAndRelationsTest {

  private final SpringDataJdbcDemoService service;
  private final CategoryRepository categoryRepository;
  private final TagRepository tagRepository;

  Section3_MappingAndRelationsTest(SpringDataJdbcDemoService service,
                                   CategoryRepository categoryRepository,
                                   TagRepository tagRepository) {
    this.service = service;
    this.categoryRepository = categoryRepository;
    this.tagRepository = tagRepository;
  }

  @BeforeEach
  void setUp() {
    categoryRepository.deleteAll();
    tagRepository.deleteAll();

    Tag popular = tagRepository.save(new Tag("popular"));
    Tag sale = tagRepository.save(new Tag("sale"));
    Tag premium = tagRepository.save(new Tag("premium"));

    Category electronics = categoryRepository.save(new Category("Electronics"));
    electronics.addProduct(new CategoryProduct("Phone", "DEVICE", BigDecimal.valueOf(999),
                                               new ProductDetails("Acme", "SKU-PHONE-1"), AggregateReference.to(popular.getId())));
    electronics.addProduct(new CategoryProduct("Laptop", "DEVICE", BigDecimal.valueOf(1999),
                                               new ProductDetails("Acme", "SKU-LAPTOP-1"), AggregateReference.to(premium.getId())));
    categoryRepository.save(electronics);

    Category books = categoryRepository.save(new Category("Books"));
    books.addProduct(new CategoryProduct("Novel", "BOOK", BigDecimal.valueOf(19),
                                         new ProductDetails("Books Inc", "SKU-NOVEL-1"), AggregateReference.to(popular.getId())));
    books.addProduct(new CategoryProduct("Cookbook", "BOOK", BigDecimal.valueOf(29),
                                         new ProductDetails("Books Inc", "SKU-COOK-1"), AggregateReference.to(sale.getId())));
    categoryRepository.save(books);
  }

  @Test
  void shouldLoadElectronicsAggregateWithExactProductsAndEmbeddedDetails() {
    Long electronicsId = categoryRepository.findByTitle("Electronics").orElseThrow().getId();

    CategoryDto dto = service.getCategory(electronicsId);

    assertThat(dto.id()).isEqualTo(electronicsId);
    assertThat(dto.title()).isEqualTo("Electronics");
    assertThat(dto.products()).hasSize(2);

    ProductDto phone = findProduct(dto, "Phone");
    assertThat(phone.id()).isNotNull();
    assertThat(phone.name()).isEqualTo("Phone");
    assertThat(phone.type()).isEqualTo("DEVICE");
    assertThat(phone.price()).isEqualByComparingTo("999");
    assertThat(phone.manufacturer()).isEqualTo("Acme");
    assertThat(phone.sku()).isEqualTo("SKU-PHONE-1");

    ProductDto laptop = findProduct(dto, "Laptop");
    assertThat(laptop.id()).isNotNull();
    assertThat(laptop.name()).isEqualTo("Laptop");
    assertThat(laptop.type()).isEqualTo("DEVICE");
    assertThat(laptop.price()).isEqualByComparingTo("1999");
    assertThat(laptop.manufacturer()).isEqualTo("Acme");
    assertThat(laptop.sku()).isEqualTo("SKU-LAPTOP-1");
  }

  @Test
  void shouldExposeTagIdsForCrossAggregateReferences() {
    Long electronicsId = categoryRepository.findByTitle("Electronics").orElseThrow().getId();
    Long popularTagId = tagRepository.findByName("popular").orElseThrow().getId();
    Long premiumTagId = tagRepository.findByName("premium").orElseThrow().getId();

    CategoryDto dto = service.getCategory(electronicsId);

    ProductDto phone = findProduct(dto, "Phone");
    ProductDto laptop = findProduct(dto, "Laptop");

    assertThat(phone.tagId()).isEqualTo(popularTagId);
    assertThat(laptop.tagId()).isEqualTo(premiumTagId);
  }

  private ProductDto findProduct(CategoryDto category, String name) {
    return category.products().stream()
        .filter(product -> product.name().equals(name))
        .findFirst()
        .orElseThrow(() -> new AssertionError("Product not found: " + name));
  }
}