package com.example.springdatajdbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("customer_order")
public class CustomerOrder {

  @Id
  private Long id;

  private String description;

  public CustomerOrder() {
  }

  public CustomerOrder(String description) {
    this.description = description;
  }

  public Long getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
