package com.example.springrest.dto;

public record ValidationError(
    String field,
    String rejectedValue,
    String message
) {
}
