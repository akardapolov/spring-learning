package com.example.servlets.servlet;

import com.example.servlets.model.ApiModels;
import com.example.servlets.util.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet демонстрация обработки ошибок.
 */
@WebServlet(urlPatterns = "/api/error/*", loadOnStartup = 5)
public class ErrorHandlingServlet extends HttpServlet {

  private final ObjectMapper objectMapper = JsonUtils.objectMapper();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String pathInfo = req.getPathInfo();

    if (pathInfo == null || pathInfo.equals("/")) {
      sendJsonResponse(resp, new ApiModels.HttpResponse(200, "Error handling demo", Map.of(
          "description", "Error handling in servlets",
          "endpoints", Map.of(
              "/api/error/bad-request", "400 Bad Request",
              "/api/error/not-found", "404 Not Found",
              "/api/error/forbidden", "403 Forbidden",
              "/api/error/server-error", "500 Internal Server Error",
              "/api/error/runtime", "Throws RuntimeException",
              "/api/error/illegal-argument", "Throws IllegalArgumentException",
              "/api/error/null-pointer", "Throws NullPointerException"
          )
      )));
    } else if (pathInfo.equals("/bad-request")) {
      sendErrorResponse(resp, 400, "Bad Request", "Invalid request", req.getRequestURI());
    } else if (pathInfo.equals("/not-found")) {
      sendErrorResponse(resp, 404, "Not Found", "Resource not found", req.getRequestURI());
    } else if (pathInfo.equals("/forbidden")) {
      sendErrorResponse(resp, 403, "Forbidden", "Access denied", req.getRequestURI());
    } else if (pathInfo.equals("/server-error")) {
      sendErrorResponse(resp, 500, "Internal Server Error", "Something went wrong", req.getRequestURI());
    } else if (pathInfo.equals("/runtime")) {
      throw new RuntimeException("This is a runtime exception");
    } else if (pathInfo.equals("/illegal-argument")) {
      throw new IllegalArgumentException("Invalid argument provided");
    } else if (pathInfo.equals("/null-pointer")) {
      throw new NullPointerException("Null pointer exception demo");
    } else {
      sendErrorResponse(resp, 404, "Not Found", "Endpoint not found", req.getRequestURI());
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String contentType = req.getContentType();
    if (contentType == null || !contentType.contains("application/json")) {
      sendErrorResponse(resp, 415, "Unsupported Media Type", "Content-Type must be application/json", req.getRequestURI());
      return;
    }

    try {
      Map<String, Object> body = objectMapper.readValue(req.getInputStream(), Map.class);
      if (!body.containsKey("name")) {
        sendErrorResponse(resp, 400, "Bad Request", "Name field is required", req.getRequestURI());
        return;
      }
      sendJsonResponse(resp, new ApiModels.HttpResponse(201, "Created", body));
    } catch (Exception e) {
      sendErrorResponse(resp, 400, "Bad Request", "Invalid JSON: " + e.getMessage(), req.getRequestURI());
    }
  }

  private void sendJsonResponse(HttpServletResponse resp, Object obj) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    PrintWriter writer = resp.getWriter();
    objectMapper.writeValue(writer, obj);
  }

  private void sendErrorResponse(HttpServletResponse resp, int status, String error, String message, String path) throws IOException {
    ApiModels.ErrorResponse errorResponse = new ApiModels.ErrorResponse(status, error, message, path);
    resp.setStatus(status);
    sendJsonResponse(resp, errorResponse);
  }
}
