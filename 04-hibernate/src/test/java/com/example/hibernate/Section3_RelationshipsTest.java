package com.example.hibernate;

import com.example.hibernate.entity.Category;
import com.example.hibernate.entity.Customer;
import com.example.hibernate.entity.CustomerOrder;
import com.example.hibernate.entity.Product;
import com.example.hibernate.entity.Tag;
import com.example.hibernate.repository.RelationshipRepository;
import java.util.SequencedCollection;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section3_RelationshipsTest {

  private final RelationshipRepository repository;

  Section3_RelationshipsTest(RelationshipRepository repository) {
    this.repository = repository;
  }

  @Test
  @Transactional
  void shouldLoadCategoryProductsRelationship() {
    SequencedCollection<Category> categories = repository.findAllCategories();

    assertThat(categories).isNotEmpty();

    Category category = categories.getFirst();
    assertThat(category.getProducts()).isNotNull();
    assertThat(category.getProducts()).isNotEmpty();

    Product product = category.getProducts().getFirst();
    assertThat(product.getName()).isNotBlank();
    assertThat(product.getCategory()).isNotNull();
    assertThat(product.getCategory().getId()).isEqualTo(category.getId());
  }

  @Test
  @Transactional
  void shouldLoadProductCategoryRelationship() {
    Product product = repository.findAnyProduct();

    assertThat(product).isNotNull();
    assertThat(product.getCategory()).isNotNull();
    assertThat(product.getCategory().getTitle()).isNotBlank();
  }

  @Test
  @Transactional
  void shouldLoadCustomerOrdersRelationship() {
    SequencedCollection<Customer> customers = repository.findAllCustomers();

    assertThat(customers).isNotEmpty();

    Customer customer = customers.getFirst();
    assertThat(customer.getOrders()).isNotNull();
    assertThat(customer.getOrders()).isNotEmpty();

    CustomerOrder order = customer.getOrders().getFirst();
    assertThat(order.getDescription()).isNotBlank();
    assertThat(order.getCustomer()).isNotNull();
    assertThat(order.getCustomer().getId()).isEqualTo(customer.getId());
  }

  @Test
  @Transactional
  void shouldLoadOrderCustomerRelationship() {
    CustomerOrder order = repository.findAnyOrder();

    assertThat(order).isNotNull();
    assertThat(order.getCustomer()).isNotNull();
    assertThat(order.getCustomer().getName()).isNotBlank();
  }

  @Test
  @Transactional
  void shouldLoadTagProductsRelationship() {
    SequencedCollection<Tag> tags = repository.findAllTags();

    assertThat(tags).isNotEmpty();

    Tag tag = tags.getFirst();
    assertThat(tag.getProducts()).isNotNull();
    assertThat(tag.getProducts()).isNotEmpty();

    Product product = tag.getProducts().iterator().next();
    assertThat(product.getName()).isNotBlank();
    assertThat(product.getTags()).isNotNull();
  }

  @Test
  @Transactional
  void shouldLoadProductTagsRelationship() {
    Product product = repository.findAnyProduct();

    assertThat(product).isNotNull();
    assertThat(product.getTags()).isNotNull();
  }
}