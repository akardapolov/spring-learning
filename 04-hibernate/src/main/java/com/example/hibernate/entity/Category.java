package com.example.hibernate.entity;

import jakarta.persistence.Cacheable;
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
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "categories")
@NamedEntityGraph(
    name = "category-with-products",
    attributeNodes = @NamedAttributeNode("products")
)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "category-cache")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @BatchSize(size = 10)
  @Fetch(FetchMode.SUBSELECT)
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