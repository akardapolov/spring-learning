package com.example.hibernate.springdata;

import com.example.hibernate.entity.Product;
import java.math.BigDecimal;
import org.springframework.data.jpa.domain.Specification;

public final class ProductSpecification {

  private ProductSpecification() {
  }

  public static Specification<Product> hasType(String type) {
    return (root, query, cb) -> cb.equal(root.get("type"), type);
  }

  public static Specification<Product> priceGreaterThan(BigDecimal price) {
    return (root, query, cb) -> cb.greaterThan(root.get("price"), price);
  }

  public static Specification<Product> nameContains(String text) {
    return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + text.toLowerCase() + "%");
  }
}