package com.example.springrest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Schema(description = "Request object for creating a new product")
public record ProductCreateRequest(
    @Schema(description = "Product name (2-100 characters)", example = "Laptop Pro", required = true)
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    String name,

    @Schema(description = "Product type (2-50 characters)", example = "Electronics", required = true)
    @NotBlank(message = "Type is required")
    @Size(min = 2, max = 50, message = "Type must be between 2 and 50 characters")
    String type,

    @Schema(description = "Product price (minimum 0.01)", example = "1299.99", required = true)
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    BigDecimal price,

    @Schema(description = "Manufacturer name", example = "TechCorp")
    String manufacturer,

    @Schema(description = "Stock keeping unit (2-50 characters)", example = "LAP-001")
    @Size(min = 2, max = 50, message = "SKU must be between 2 and 50 characters")
    String sku,

    @Schema(description = "Category ID", example = "1")
    Long categoryId
) {
}
