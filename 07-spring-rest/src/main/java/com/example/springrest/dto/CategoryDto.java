package com.example.springrest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Category data transfer object")
public record CategoryDto(
    @Schema(description = "Category unique identifier", example = "1")
    Long id,

    @Schema(description = "Category title", example = "Electronics", required = true)
    String title
) {
}
