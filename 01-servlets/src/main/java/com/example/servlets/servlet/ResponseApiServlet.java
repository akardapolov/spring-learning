package com.example.servlets.servlet;

import com.example.servlets.model.ApiModels;
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
 * Servlet демонстрация Response API.
 */
@WebServlet(urlPatterns = {"/api/response", "/api/response/*"}, loadOnStartup = 2)
public class ResponseApiServlet extends HttpServlet {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String pathInfo = req.getPathInfo();

    if (pathInfo == null || pathInfo.equals("/")) {
      // Basic response
      sendJsonResponse(resp, new ApiModels.HttpResponse(200, "Response API demo", Map.of(
          "description", "HttpServletResponse позволяет формировать ответ клиенту",
          "endpoints", Map.of(
              "/api/response/status/{code}", "Test status codes",
              "/api/response/headers", "Response headers",
              "/api/response/content?type=html|text|xml", "Content types",
              "/api/response/redirect?to=/path", "Redirect",
              "/api/response/encoding", "Character encoding",
              "/api/response/buffer", "Buffer management"
          )
      )));
    } else if (pathInfo.startsWith("/status/")) {
      handleStatusCode(req, resp, pathInfo);
    } else if (pathInfo.equals("/headers")) {
      handleHeaders(resp);
    } else if (pathInfo.equals("/content")) {
      handleContent(req, resp);
    } else if (pathInfo.equals("/redirect")) {
      handleRedirect(req, resp);
    } else if (pathInfo.equals("/encoding")) {
      handleEncoding(resp);
    } else if (pathInfo.equals("/buffer")) {
      handleBuffer(resp);
    } else {
      sendErrorResponse(resp, 404, "Not Found", "Endpoint not found", req.getRequestURI());
    }
  }

  private void handleStatusCode(HttpServletRequest req, HttpServletResponse resp, String pathInfo) throws IOException {
    String codeStr = pathInfo.substring("/status/".length());
    try {
      int code = Integer.parseInt(codeStr);
      resp.setStatus(code);
      String message = getStatusMessage(code);
      sendJsonResponse(resp, new ApiModels.HttpResponse(code, message, Map.of(
          "status", code,
          "message", message
      )));
    } catch (NumberFormatException e) {
      sendErrorResponse(resp, 400, "Bad Request", "Invalid status code", req.getRequestURI());
    }
  }

  private void handleHeaders(HttpServletResponse resp) throws IOException {
    resp.setHeader("X-Custom-Header", "CustomValue");
    resp.setHeader("X-Request-Id", "req-" + System.currentTimeMillis());
    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    sendJsonResponse(resp, new ApiModels.HttpResponse(200, "Headers demo", Map.of(
        "headers", Map.of(
            "X-Custom-Header", resp.getHeader("X-Custom-Header"),
            "X-Request-Id", resp.getHeader("X-Request-Id"),
            "Cache-Control", resp.getHeader("Cache-Control")
        )
    )));
  }

  private void handleContent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String type = req.getParameter("type");

    switch (type != null ? type : "json") {
      case "html":
        resp.setContentType("text/html");
        resp.getWriter().write("<h1>HTML Response</h1><p>This is HTML content</p>");
        break;
      case "text":
        resp.setContentType("text/plain");
        resp.getWriter().write("This is plain text content");
        break;
      case "xml":
        resp.setContentType("application/xml");
        resp.getWriter().write("<?xml version=\"1.0\"?><response><message>XML Content</message></response>");
        break;
      default:
        resp.setContentType("application/json");
        sendJsonResponse(resp, new ApiModels.HttpResponse(200, "JSON response", Map.of("content", "JSON")));
    }
  }

  private void handleRedirect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String to = req.getParameter("to");
    if (to == null) {
      to = "/api/demo";
    }
    resp.sendRedirect(to);
  }

  private void handleEncoding(HttpServletResponse resp) throws IOException {
    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("application/json; charset=UTF-8");
    sendJsonResponse(resp, new ApiModels.HttpResponse(200, "Encoding demo", Map.of(
        "encoding", resp.getCharacterEncoding(),
        "contentType", resp.getContentType(),
        "message", "Привет мир! Hello World!"
    )));
  }

  private void handleBuffer(HttpServletResponse resp) throws IOException {
    resp.setBufferSize(8192);
    int bufferSize = resp.getBufferSize();
    boolean isCommitted = resp.isCommitted();
    resp.resetBuffer();
    sendJsonResponse(resp, new ApiModels.HttpResponse(200, "Buffer demo", Map.of(
        "bufferSize", bufferSize,
        "isCommitted", isCommitted,
        "bufferAfterReset", resp.getBufferSize()
    )));
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

  private String getStatusMessage(int code) {
    return switch (code) {
      case 200 -> "OK";
      case 201 -> "Created";
      case 204 -> "No Content";
      case 301 -> "Moved Permanently";
      case 302 -> "Found";
      case 400 -> "Bad Request";
      case 401 -> "Unauthorized";
      case 403 -> "Forbidden";
      case 404 -> "Not Found";
      case 405 -> "Method Not Allowed";
      case 500 -> "Internal Server Error";
      case 503 -> "Service Unavailable";
      default -> "Unknown";
    };
  }
}
