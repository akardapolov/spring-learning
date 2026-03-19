package com.example.springdatajdbc;

import com.example.springdatajdbc.model.ApiModels.CategoryDto;
import com.example.springdatajdbc.model.ApiModels.CustomerDto;
import com.example.springdatajdbc.service.SpringDataJdbcDemoService;
import com.example.springdatajdbc.model.ApiModels.CategoryCreateRequest;
import com.example.springdatajdbc.model.ApiModels.CategoryUpdateRequest;
import com.example.springdatajdbc.model.ApiModels.CustomerCreateRequest;
import com.example.springdatajdbc.model.ApiModels.OrderCreateRequest;
import com.example.springdatajdbc.model.ApiModels.ProductCreateRequest;
import com.example.springdatajdbc.model.ApiModels.ProductUpdateRequest;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section7_CrudApiTest {

  private final SpringDataJdbcDemoService service;

  Section7_CrudApiTest(SpringDataJdbcDemoService service) {
    this.service = service;
  }

  @Test
  void shouldCreateAndUpdateCategory() {
    CategoryDto created = service.createCategory(new CategoryCreateRequest("Sports"));
    assertThat(created.id()).isNotNull();
    assertThat(created.title()).isEqualTo("Sports");

    CategoryDto updated = service.updateCategory(created.id(), new CategoryUpdateRequest("Sports & Outdoor"));
    assertThat(updated.title()).isEqualTo("Sports & Outdoor");
  }

  @Test
  void shouldAddAndUpdateAndRemoveProduct() {
    CategoryDto cat = service.createCategory(new CategoryCreateRequest("Toys"));

    CategoryDto withProduct = service.addProduct(cat.id(),
        new ProductCreateRequest("Ball", "TOY", BigDecimal.valueOf(15), "ToyMaker", "SKU-BALL", null));
    assertThat(withProduct.products()).hasSize(1);

    Long prodId = withProduct.products().getFirst().id();

    CategoryDto updatedProd = service.updateProduct(cat.id(), prodId,
        new ProductUpdateRequest("Big Ball", "TOY", BigDecimal.valueOf(25), "ToyMaker", "SKU-BBALL", null));
    assertThat(updatedProd.products().getFirst().name()).isEqualTo("Big Ball");

    CategoryDto removed = service.removeProduct(cat.id(), prodId);
    assertThat(removed.products()).isEmpty();
  }

  @Test
  void shouldDeleteCategory() {
    CategoryDto cat = service.createCategory(new CategoryCreateRequest("Temp"));
    service.deleteCategory(cat.id());

    assertThatThrownBy(() -> service.getCategory(cat.id()))
        .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void shouldCreateCustomerAndAddOrders() {
    CustomerDto customer = service.createCustomer(new CustomerCreateRequest("Anna", "anna@test.com"));
    assertThat(customer.id()).isNotNull();
    assertThat(customer.orders()).isEmpty();

    CustomerDto withOrder = service.addOrder(customer.id(), new OrderCreateRequest("New order"));
    assertThat(withOrder.orders()).hasSize(1);
    assertThat(withOrder.orders().getFirst().description()).isEqualTo("New order");
  }
}
