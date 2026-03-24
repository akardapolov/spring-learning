package com.example.springrest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Product data transfer object")
public record ProductDto(
    @Schema(description = "Product unique identifier", example = "1")
    Long id,

    @Schema(description = "Product name", example = "Laptop Pro", required = true)
    String name,

    @Schema(description = "Product type/category", example = "Electronics", required = true)
    String type,

    @Schema(description = "Product price", example = "1299.99", required = true)
    BigDecimal price,

    @Schema(description = "Optimistic locking version", example = "1")
    Long version,

    @Schema(description = "Manufacturer name", example = "TechCorp")
    String manufacturer,

    @Schema(description = "Stock keeping unit", example = "LAP-001")
    String sku,

    @Schema(description = "Product category information")
    CategoryDto category
) {
}
