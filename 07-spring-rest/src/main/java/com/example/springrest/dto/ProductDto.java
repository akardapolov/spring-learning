package com.example.springrest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDto(
    Long id,
    String name,
    String type,
    BigDecimal price,
    Long version,
    String manufacturer,
    String sku,
    CategoryDto category
) {
}
