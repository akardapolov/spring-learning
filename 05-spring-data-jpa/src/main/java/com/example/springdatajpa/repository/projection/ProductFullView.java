package com.example.springdatajpa.repository.projection;

import java.math.BigDecimal;
import java.util.Set;

public interface ProductFullView {

  Long getId();
  String getName();
  BigDecimal getPrice();
  String getType();

  CategoryView getCategory();

  Set<TagView> getTags();

  interface CategoryView {
    Long getId();
    String getTitle();
  }

  interface TagView {
    Long getId();
    String getName();
  }
}