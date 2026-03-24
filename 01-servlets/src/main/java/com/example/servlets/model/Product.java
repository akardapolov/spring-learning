package com.example.servlets.model;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Product entity for demo purposes.
 */
public record Product(
    Long id,
    String name,
    BigDecimal price,
    String category,
    String description,
    Set<Tag> tags
) {
  public Product(Long id, String name, BigDecimal price, String category) {
    this(id, name, price, category, null, null);
  }

  public Product {
    if (tags == null) {
      tags = java.util.Collections.emptySet();
    }
  }
}
