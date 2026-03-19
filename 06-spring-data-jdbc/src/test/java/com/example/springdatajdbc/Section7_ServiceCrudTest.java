package com.example.springdatajdbc;

import com.example.springdatajdbc.config.LifecycleEventsCollector;
import com.example.springdatajdbc.model.ApiModels.CategoryCreateRequest;
import com.example.springdatajdbc.model.ApiModels.CategoryDto;
import com.example.springdatajdbc.model.ApiModels.CategoryUpdateRequest;
import com.example.springdatajdbc.model.ApiModels.CustomerCreateRequest;
import com.example.springdatajdbc.model.ApiModels.CustomerDto;
import com.example.springdatajdbc.model.ApiModels.OrderCreateRequest;
import com.example.springdatajdbc.model.ApiModels.ProductCreateRequest;
import com.example.springdatajdbc.model.ApiModels.ProductDto;
import com.example.springdatajdbc.model.ApiModels.ProductUpdateRequest;
import com.example.springdatajdbc.service.SpringDataJdbcDemoService;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJdbcTest
@Import({SpringDataJdbcDemoService.class, LifecycleEventsCollector.class})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section7_ServiceCrudTest {

  private final SpringDataJdbcDemoService service;

  Section7_ServiceCrudTest(SpringDataJdbcDemoService service) {
    this.service = service;
  }

  @Test
  void shouldCreateAndUpdateCategoryAndPersistChangedTitle() {
    CategoryDto created = service.createCategory(new CategoryCreateRequest("Sports"));

    assertThat(created.id()).isNotNull();
    assertThat(created.title()).isEqualTo("Sports");
    assertThat(created.products()).isEmpty();

    CategoryDto reloadedAfterCreate = service.getCategory(created.id());
    assertThat(reloadedAfterCreate.title()).isEqualTo("Sports");

    CategoryDto updated = service.updateCategory(created.id(), new CategoryUpdateRequest("Sports & Outdoor"));

    assertThat(updated.id()).isEqualTo(created.id());
    assertThat(updated.title()).isEqualTo("Sports & Outdoor");

    CategoryDto reloadedAfterUpdate = service.getCategory(created.id());
    assertThat(reloadedAfterUpdate.title()).isEqualTo("Sports & Outdoor");
  }

  @Test
  void shouldAddUpdateAndRemoveProductAndPersistAggregateState() {
    CategoryDto category = service.createCategory(new CategoryCreateRequest("Toys"));

    CategoryDto afterAdd = service.addProduct(category.id(),
                                              new ProductCreateRequest("Ball", "TOY", BigDecimal.valueOf(15), "ToyMaker", "SKU-BALL", null));

    assertThat(afterAdd.products()).hasSize(1);
    ProductDto createdProduct = afterAdd.products().get(0);
    assertThat(createdProduct.id()).isNotNull();
    assertProduct(createdProduct, "Ball", "TOY", "15", "ToyMaker", "SKU-BALL", null);

    CategoryDto reloadedAfterAdd = service.getCategory(category.id());
    assertThat(reloadedAfterAdd.products()).hasSize(1);
    assertProduct(reloadedAfterAdd.products().get(0), "Ball", "TOY", "15", "ToyMaker", "SKU-BALL", null);

    CategoryDto afterUpdate = service.updateProduct(category.id(), createdProduct.id(),
                                                    new ProductUpdateRequest("Big Ball", "TOY", BigDecimal.valueOf(25), "ToyMaker", "SKU-BBALL", null));

    assertThat(afterUpdate.products()).hasSize(1);
    ProductDto updatedProduct = afterUpdate.products().get(0);
    assertThat(updatedProduct.id()).isEqualTo(createdProduct.id());
    assertProduct(updatedProduct, "Big Ball", "TOY", "25", "ToyMaker", "SKU-BBALL", null);

    CategoryDto reloadedAfterUpdate = service.getCategory(category.id());
    assertThat(reloadedAfterUpdate.products()).hasSize(1);
    assertProduct(reloadedAfterUpdate.products().get(0), "Big Ball", "TOY", "25", "ToyMaker", "SKU-BBALL", null);

    CategoryDto afterRemove = service.removeProduct(category.id(), createdProduct.id());
    assertThat(afterRemove.products()).isEmpty();

    CategoryDto reloadedAfterRemove = service.getCategory(category.id());
    assertThat(reloadedAfterRemove.products()).isEmpty();
  }

  @Test
  void shouldDeleteCategoryAndThenRejectSubsequentRead() {
    CategoryDto category = service.createCategory(new CategoryCreateRequest("Temp"));

    service.deleteCategory(category.id());

    assertThatThrownBy(() -> service.getCategory(category.id()))
        .isInstanceOf(NoSuchElementException.class)
        .hasMessage("Category not found: " + category.id());
  }

  @Test
  void shouldCreateCustomerAndAddOrderAndPersistCustomerAggregate() {
    CustomerDto created = service.createCustomer(new CustomerCreateRequest("Anna", "anna@test.com"));

    assertThat(created.id()).isNotNull();
    assertThat(created.name()).isEqualTo("Anna");
    assertThat(created.email()).isEqualTo("anna@test.com");
    assertThat(created.orders()).isEmpty();

    CustomerDto afterAddOrder = service.addOrder(created.id(), new OrderCreateRequest("New order"));

    assertThat(afterAddOrder.orders()).hasSize(1);
    assertThat(afterAddOrder.orders().get(0).id()).isNotNull();
    assertThat(afterAddOrder.orders().get(0).description()).isEqualTo("New order");

    CustomerDto reloaded = service.getCustomer(created.id());
    assertThat(reloaded.name()).isEqualTo("Anna");
    assertThat(reloaded.email()).isEqualTo("anna@test.com");
    assertThat(reloaded.orders()).hasSize(1);
    assertThat(reloaded.orders().get(0).description()).isEqualTo("New order");
  }

  @Test
  void shouldThrowWhenUpdatingUnknownCategory() {
    assertThatThrownBy(() -> service.updateCategory(99999L, new CategoryUpdateRequest("Missing")))
        .isInstanceOf(NoSuchElementException.class)
        .hasMessage("Category not found: 99999");
  }

  @Test
  void shouldThrowWhenRemovingUnknownProduct() {
    CategoryDto category = service.createCategory(new CategoryCreateRequest("Empty"));

    assertThatThrownBy(() -> service.removeProduct(category.id(), 99999L))
        .isInstanceOf(NoSuchElementException.class)
        .hasMessage("Product not found: 99999");
  }

  @Test
  void shouldThrowWhenAddingOrderToUnknownCustomer() {
    assertThatThrownBy(() -> service.addOrder(99999L, new OrderCreateRequest("Ghost order")))
        .isInstanceOf(NoSuchElementException.class)
        .hasMessage("Customer not found: 99999");
  }

  private void assertProduct(ProductDto product,
                             String expectedName,
                             String expectedType,
                             String expectedPrice,
                             String expectedManufacturer,
                             String expectedSku,
                             Long expectedTagId) {
    assertThat(product.name()).isEqualTo(expectedName);
    assertThat(product.type()).isEqualTo(expectedType);
    assertThat(product.price()).isEqualByComparingTo(expectedPrice);
    assertThat(product.manufacturer()).isEqualTo(expectedManufacturer);
    assertThat(product.sku()).isEqualTo(expectedSku);
    assertThat(product.tagId()).isEqualTo(expectedTagId);
  }
}