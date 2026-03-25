package com.example.servlets.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {
  private JsonUtils() {}

  public static ObjectMapper objectMapper() {
    return new ObjectMapper().findAndRegisterModules();
  }
}