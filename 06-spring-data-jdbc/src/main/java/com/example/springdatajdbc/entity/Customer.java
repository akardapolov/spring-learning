package com.example.springdatajdbc.entity;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("customers")
public class Customer {

  @Id
  private Long id;

  private String name;
  private String email;

  @MappedCollection(idColumn = "customer_id", keyColumn = "order_key")
  private List<CustomerOrder> orders = new ArrayList<>();

  public Customer() {
  }

  public Customer(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public void addOrder(CustomerOrder order) {
    orders.add(order);
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<CustomerOrder> getOrders() {
    return orders;
  }

  public void setOrders(List<CustomerOrder> orders) {
    this.orders = orders;
  }
}