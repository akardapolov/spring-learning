package com.example.hibernate;

import com.example.hibernate.entity.Product;
import com.example.hibernate.entity.ProductDetails;
import com.example.hibernate.model.ApiModels.OptimisticLockingResult;
import com.example.hibernate.service.OptimisticLockingDemoService;
import com.example.hibernate.springdata.ProductSpringDataRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section8_OptimisticLockingTest {

  private final OptimisticLockingDemoService service;
  private final ProductSpringDataRepository repository;

  Section8_OptimisticLockingTest(
      OptimisticLockingDemoService service,
      ProductSpringDataRepository repository
  ) {
    this.service = service;
    this.repository = repository;
  }

  @Test
  void shouldTriggerOptimisticLocking() {
    Product product = repository.save(
        new Product(
            "Optimistic Test Product",
            "TEST",
            BigDecimal.valueOf(100),
            new ProductDetails("Test Inc", "OPT-LOCK-1")
        )
    );

    Long productId = product.getId();

    OptimisticLockingResult result = service.demonstrate(productId);

    assertThat(result.productId()).isEqualTo(productId);
    assertThat(result.initialVersionTx1()).isNotNull();
    assertThat(result.initialVersionTx2()).isNotNull();
    assertThat(result.initialVersionTx1()).isEqualTo(result.initialVersionTx2());

    assertThat(result.tx1Committed()).isTrue();
    assertThat(result.tx2Committed()).isFalse();
    assertThat(result.optimisticLockTriggered()).isTrue();
    assertThat(result.error()).isNotBlank();

    Product reloaded = repository.findById(productId).orElseThrow();
    assertThat(reloaded.getPrice()).isEqualByComparingTo("101");
    assertThat(reloaded.getVersion()).isEqualTo(result.initialVersionTx1() + 1);
  }
}