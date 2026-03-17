package com.example.springdatajpa.dto;

import java.math.BigDecimal;

public record TypeStatsDto(
    String type,
    long productCount,
    BigDecimal avgPrice,
    BigDecimal minPrice,
    BigDecimal maxPrice
) {}