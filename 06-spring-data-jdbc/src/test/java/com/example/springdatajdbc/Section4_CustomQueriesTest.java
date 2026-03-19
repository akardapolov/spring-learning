package com.example.springdatajdbc;

import com.example.springdatajdbc.config.LifecycleEventsCollector;
import com.example.springdatajdbc.entity.Category;
import com.example.springdatajdbc.entity.CategoryProduct;
import com.example.springdatajdbc.entity.ProductDetails;
import com.example.springdatajdbc.entity.Tag;
import com.example.springdatajdbc.model.ApiModels.CustomQueryResult;
import com.example.springdatajdbc.repository.CategoryRepository;
import com.example.springdatajdbc.repository.TagRepository;
import com.example.springdatajdbc.service.SpringDataJdbcDemoService;
import java.math.BigDecimal;
import java.util.List;
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
class Section4_CustomQueriesTest {

  private final SpringDataJdbcDemoService service;
  private final CategoryRepository categoryRepository;
  private final TagRepository tagRepository;

  Section4_CustomQueriesTest(SpringDataJdbcDemoService service,
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

    Category home = categoryRepository.save(new Category("Home"));
    home.addProduct(new CategoryProduct("Vacuum Cleaner", "HOME", BigDecimal.valueOf(299),
                                        new ProductDetails("HomeTech", "SKU-VAC-1"), AggregateReference.to(sale.getId())));
    home.addProduct(new CategoryProduct("Lamp", "HOME", BigDecimal.valueOf(49),
                                        new ProductDetails("HomeTech", "SKU-LAMP-1"), AggregateReference.to(popular.getId())));
    categoryRepository.save(home);
  }

  @Test
  void shouldReturnOnlyBooksForTitleFragment_oo() {
    assertThat(service.customQueryDemo("oo"))
        .isEqualTo(new CustomQueryResult(1, List.of("Books")));
  }

  @Test
  void shouldSearchByTitleCaseInsensitively() {
    assertThat(service.customQueryDemo("OO"))
        .isEqualTo(new CustomQueryResult(1, List.of("Books")));
  }

  @Test
  void shouldReturnOnlyElectronicsForMinPrice1000() {
    assertThat(service.customQueryByMinPrice(BigDecimal.valueOf(1000)))
        .isEqualTo(new CustomQueryResult(1, List.of("Electronics")));
  }

  @Test
  void shouldReturnEmptyResultForTooHighMinPrice() {
    assertThat(service.customQueryByMinPrice(BigDecimal.valueOf(5000)))
        .isEqualTo(new CustomQueryResult(0, List.of()));
  }
}