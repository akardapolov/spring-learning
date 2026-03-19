package com.example.springdatajdbc.entity;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Table("category_product")
public class CategoryProduct {

  @Id
  private Long id;

  private String name;
  private String type;
  private BigDecimal price;

  @Embedded(onEmpty = Embedded.OnEmpty.USE_EMPTY)
  private ProductDetails details;

  private AggregateReference<Tag, Long> tag;

  @Version
  private Long version;

  public CategoryProduct() {
  }

  public CategoryProduct(String name, String type, BigDecimal price,
                          ProductDetails details, AggregateReference<Tag, Long> tag) {
    this.name = name;
    this.type = type;
    this.price = price;
    this.details = details;
    this.tag = tag;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public ProductDetails getDetails() {
    return details;
  }

  public void setDetails(ProductDetails details) {
    this.details = details;
  }

  public AggregateReference<Tag, Long> getTag() {
    return tag;
  }

  public void setTag(AggregateReference<Tag, Long> tag) {
    this.tag = tag;
  }

  public Long getVersion() {
    return version;
  }
}
