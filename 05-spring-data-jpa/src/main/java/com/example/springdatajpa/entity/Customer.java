package com.example.springdatajpa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<CustomerOrder> orders = new ArrayList<>();

  public Customer() {
  }

  public Customer(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public void addOrder(CustomerOrder order) {
    orders.add(order);
    order.setCustomer(this);
  }

  public Long getId() {
    return id;
  }

  public List<CustomerOrder> getOrders() {
    return orders;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }
}