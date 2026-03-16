package com.example.hibernate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers_joined_ext")
public class CustomerJoined extends PersonJoined {

  private String segment;

  public CustomerJoined() {
  }

  public CustomerJoined(String name, String segment) {
    super(name);
    this.segment = segment;
  }

  public String getSegment() {
    return segment;
  }
}