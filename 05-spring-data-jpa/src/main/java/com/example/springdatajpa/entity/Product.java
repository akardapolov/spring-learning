package com.example.springdatajpa.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String type;
  private BigDecimal price;

  @Version
  private Long version;

  @Embedded
  private ProductDetails details;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
  private Set<Tag> tags = new HashSet<>();

  public Product() {
  }

  public Product(String name, String type, BigDecimal price) {
    this.name = name;
    this.type = type;
    this.price = price;
  }

  public Product(String name, String type, BigDecimal price, ProductDetails details) {
    this.name = name;
    this.type = type;
    this.price = price;
    this.details = details;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Long getVersion() {
    return version;
  }

  public ProductDetails getDetails() {
    return details;
  }

  public Category getCategory() {
    return category;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setDetails(ProductDetails details) {
    this.details = details;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Product other)) return false;
    return id != null && id.equals(other.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}