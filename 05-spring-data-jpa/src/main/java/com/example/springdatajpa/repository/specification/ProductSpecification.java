package com.example.springdatajpa.repository.specification;

import com.example.springdatajpa.entity.Product;
import java.math.BigDecimal;
import org.springframework.data.jpa.domain.Specification;

public final class ProductSpecification {

  private ProductSpecification() {
  }

  public static Specification<Product> hasType(String type) {
    return (root, query, cb) -> cb.equal(root.get("type"), type);
  }

  public static Specification<Product> nameContains(String text) {
    return (root, query, cb) -> {
      String escaped = escapeLike(text.toLowerCase());
      return cb.like(cb.lower(root.get("name")), "%" + escaped + "%", '\\');
    };
  }

  public static Specification<Product> priceGreaterThan(BigDecimal price) {
    return (root, query, cb) -> cb.greaterThan(root.get("price"), price);
  }

  private static String escapeLike(String value) {
    return value
        .replace("\\", "\\\\")
        .replace("%", "\\%")
        .replace("_", "\\_");
  }
}