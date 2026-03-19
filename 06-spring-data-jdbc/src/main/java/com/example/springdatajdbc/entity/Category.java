package com.example.springdatajdbc.entity;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("categories")
public class Category {

  @Id
  private Long id;

  private String title;

  @MappedCollection(idColumn = "category_id", keyColumn = "product_key")
  private List<CategoryProduct> products = new ArrayList<>();

  public Category() {
  }

  public Category(String title) {
    this.title = title;
  }

  public void addProduct(CategoryProduct product) {
    products.add(product);
  }

  public void removeProduct(CategoryProduct product) {
    products.remove(product);
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<CategoryProduct> getProducts() {
    return products;
  }

  public void setProducts(List<CategoryProduct> products) {
    this.products = products;
  }
}