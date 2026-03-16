package com.example.hibernate.springdata;

import java.math.BigDecimal;

public interface ProductView {

  Long getId();

  String getName();

  BigDecimal getPrice();
}