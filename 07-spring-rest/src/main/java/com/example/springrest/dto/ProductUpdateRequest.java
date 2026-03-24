package com.example.springrest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Schema(description = "Request object for updating an existing product. All fields are optional for PATCH.")
public record ProductUpdateRequest(
    @Schema(description = "Product name (2-100 characters)", example = "Laptop Pro Updated")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    String name,

    @Schema(description = "Product type (2-50 characters)", example = "Electronics")
    @Size(min = 2, max = 50, message = "Type must be between 2 and 50 characters")
    String type,

    @Schema(description = "Product price (minimum 0.01)", example = "1199.99")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    BigDecimal price,

    @Schema(description = "Manufacturer name", example = "TechCorp")
    String manufacturer,

    @Schema(description = "Stock keeping unit (2-50 characters)", example = "LAP-002")
    @Size(min = 2, max = 50, message = "SKU must be between 2 and 50 characters")
    String sku,

    @Schema(description = "Category ID", example = "1")
    Long categoryId
) {
}
