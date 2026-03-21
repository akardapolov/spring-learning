package com.example.springrest.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductUpdateRequest(
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    String name,

    @Size(min = 2, max = 50, message = "Type must be between 2 and 50 characters")
    String type,

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    BigDecimal price,

    String manufacturer,

    @Size(min = 2, max = 50, message = "SKU must be between 2 and 50 characters")
    String sku,

    Long categoryId
) {
}
