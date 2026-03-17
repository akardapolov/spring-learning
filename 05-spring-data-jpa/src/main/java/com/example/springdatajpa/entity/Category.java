package com.example.springdatajpa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@NamedEntityGraph(
    name = "category-with-products",
    attributeNodes = @NamedAttributeNode("products")
)
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Product> products = new ArrayList<>();

  public Category() {
  }

  public Category(String title) {
    this.title = title;
  }

  public void addProduct(Product product) {
    products.add(product);
    product.setCategory(this);
  }

  public void removeProduct(Product product) {
    products.remove(product);
    product.setCategory(null);
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}