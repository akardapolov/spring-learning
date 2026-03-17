package com.example.springdatajpa.dto;

import java.math.BigDecimal;

public record ProductSummaryDto(
    Long id,
    String name,
    BigDecimal price,
    String categoryTitle
) {}