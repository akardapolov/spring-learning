package com.example.servlets.model;

import java.time.Instant;
import java.util.Map;

/**
 * API models for JSON responses.
 */
public class ApiModels {
  private ApiModels() {}

  public record RequestInfo(
      String method,
      String requestUri,
      String contextPath,
      String servletPath,
      String queryString,
      Map<String, String[]> parameters,
      Map<String, String> headers,
      String remoteAddr,
      int remotePort,
      String protocol,
      String scheme
  ) {}

  public record SessionInfo(
      String sessionId,
      boolean isNew,
      int maxInactiveInterval,
      long creationTime,
      long lastAccessedTime,
      Map<String, Object> attributes
  ) {}

  public record CookieInfo(
      String name,
      String value,
      String domain,
      String path,
      int maxAge,
      boolean secure,
      boolean httpOnly
  ) {}

  public record HttpResponse(
      int status,
      String message,
      Object data,
      Instant timestamp
  ) {
    public HttpResponse(int status, String message, Object data) {
      this(status, message, data, Instant.now());
    }
  }

  public record ErrorResponse(
      int status,
      String error,
      String message,
      String path,
      long timestamp
  ) {
    public ErrorResponse(int status, String error, String message, String path) {
      this(status, error, message, path, System.currentTimeMillis());
    }
  }
}
