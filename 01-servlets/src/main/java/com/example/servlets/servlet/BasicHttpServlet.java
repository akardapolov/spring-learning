package com.example.servlets.servlet;

import com.example.servlets.config.DemoDataInitializer;
import com.example.servlets.model.ApiModels;
import com.example.servlets.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Базовый HttpServlet демонстрация CRUD операций.
 */
@WebServlet(urlPatterns = {"/api/basic/products/*", "/api/basic/products"}, loadOnStartup = 1)
public class BasicHttpServlet extends HttpServlet {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    String pathInfo = req.getPathInfo();

    if (pathInfo == null || pathInfo.equals("/")) {
      // Return all products
      sendJsonResponse(resp, new ApiModels.HttpResponse(
          200, "Products retrieved successfully", getAllProducts(req)
      ));
    } else {
      // Return product by ID
      try {
        Long id = Long.parseLong(pathInfo.substring(1));
        Product product = getProduct(req, id);
        if (product != null) {
          sendJsonResponse(resp, new ApiModels.HttpResponse(
              200, "Product retrieved successfully", product
          ));
        } else {
          sendErrorResponse(resp, 404, "Not Found", "Product not found", req.getRequestURI());
        }
      } catch (NumberFormatException e) {
        sendErrorResponse(resp, 400, "Bad Request", "Invalid product ID", req.getRequestURI());
      }
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    try {
      Product product = objectMapper.readValue(req.getInputStream(), Product.class);
      Product created = createProduct(req, product);
      resp.setStatus(HttpServletResponse.SC_CREATED);
      sendJsonResponse(resp, new ApiModels.HttpResponse(
          201, "Product created successfully", created
      ));
    } catch (Exception e) {
      sendErrorResponse(resp, 400, "Bad Request", "Invalid product data: " + e.getMessage(), req.getRequestURI());
    }
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    String pathInfo = req.getPathInfo();
    if (pathInfo == null || pathInfo.equals("/")) {
      sendErrorResponse(resp, 400, "Bad Request", "Product ID required", req.getRequestURI());
      return;
    }

    try {
      Long id = Long.parseLong(pathInfo.substring(1));
      Product product = objectMapper.readValue(req.getInputStream(), Product.class);
      Product updated = updateProduct(req, id, product);
      if (updated != null) {
        sendJsonResponse(resp, new ApiModels.HttpResponse(
            200, "Product updated successfully", updated
        ));
      } else {
        sendErrorResponse(resp, 404, "Not Found", "Product not found", req.getRequestURI());
      }
    } catch (NumberFormatException e) {
      sendErrorResponse(resp, 400, "Bad Request", "Invalid product ID", req.getRequestURI());
    } catch (Exception e) {
      sendErrorResponse(resp, 400, "Bad Request", "Invalid product data: " + e.getMessage(), req.getRequestURI());
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    String pathInfo = req.getPathInfo();
    if (pathInfo == null || pathInfo.equals("/")) {
      sendErrorResponse(resp, 400, "Bad Request", "Product ID required", req.getRequestURI());
      return;
    }

    try {
      Long id = Long.parseLong(pathInfo.substring(1));
      if (deleteProduct(req, id)) {
        sendJsonResponse(resp, new ApiModels.HttpResponse(
            200, "Product deleted successfully", null
        ));
      } else {
        sendErrorResponse(resp, 404, "Not Found", "Product not found", req.getRequestURI());
      }
    } catch (NumberFormatException e) {
      sendErrorResponse(resp, 400, "Bad Request", "Invalid product ID", req.getRequestURI());
    }
  }

  @Override
  protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
    resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    resp.setStatus(HttpServletResponse.SC_OK);
  }

  @Override
  protected void doHead(HttpServletRequest req, HttpServletResponse resp) {
    resp.setStatus(HttpServletResponse.SC_OK);
    resp.setContentType("application/json");
  }

  @SuppressWarnings("unchecked")
  private Map<Long, Product> getAllProducts(HttpServletRequest req) {
    return (Map<Long, Product>) req.getServletContext().getAttribute(DemoDataInitializer.PRODUCTS_KEY);
  }

  @SuppressWarnings("unchecked")
  private Product getProduct(HttpServletRequest req, Long id) {
    Map<Long, Product> products = getAllProducts(req);
    return products != null ? products.get(id) : null;
  }

  @SuppressWarnings("unchecked")
  private Product createProduct(HttpServletRequest req, Product product) {
    Map<Long, Product> products = getAllProducts(req);
    AtomicLong idGenerator = (AtomicLong) req.getServletContext().getAttribute(DemoDataInitializer.PRODUCT_ID_KEY);
    if (products == null || idGenerator == null) {
      return null;
    }

    Long id = idGenerator.getAndIncrement();
    Product newProduct = new Product(
        id,
        product.name(),
        product.price(),
        product.category(),
        product.description(),
        product.tags() != null ? product.tags() : java.util.Collections.emptySet()
    );
    products.put(id, newProduct);
    return newProduct;
  }

  @SuppressWarnings("unchecked")
  private Product updateProduct(HttpServletRequest req, Long id, Product product) {
    Map<Long, Product> products = getAllProducts(req);
    if (products == null || !products.containsKey(id)) {
      return null;
    }

    Product updated = new Product(
        id,
        product.name(),
        product.price(),
        product.category(),
        product.description(),
        product.tags() != null ? product.tags() : java.util.Collections.emptySet()
    );
    products.put(id, updated);
    return updated;
  }

  @SuppressWarnings("unchecked")
  private boolean deleteProduct(HttpServletRequest req, Long id) {
    Map<Long, Product> products = getAllProducts(req);
    return products != null && products.remove(id) != null;
  }

  private void sendJsonResponse(HttpServletResponse resp, Object obj) throws IOException {
    PrintWriter writer = resp.getWriter();
    objectMapper.writeValue(writer, obj);
  }

  private void sendErrorResponse(HttpServletResponse resp, int status, String error, String message, String path) throws IOException {
    ApiModels.ErrorResponse errorResponse = new ApiModels.ErrorResponse(status, error, message, path);
    resp.setStatus(status);
    sendJsonResponse(resp, errorResponse);
  }
}
