package com.example.springrest.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
    int status,
    String error,
    String message,
    LocalDateTime timestamp,
    String path,
    List<ValidationError> validationErrors
) {
}
