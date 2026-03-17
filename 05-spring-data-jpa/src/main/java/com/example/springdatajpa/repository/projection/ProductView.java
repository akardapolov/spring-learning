package com.example.springdatajpa.repository.projection;

import java.math.BigDecimal;

public interface ProductView {

  Long getId();

  String getName();

  BigDecimal getPrice();
}