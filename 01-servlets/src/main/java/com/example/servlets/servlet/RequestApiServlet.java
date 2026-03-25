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
 * Servlet демонстрация Request API.
 */
@WebServlet(urlPatterns = "/api/request", loadOnStartup = 2)
public class RequestApiServlet extends HttpServlet {

  private final ObjectMapper objectMapper = JsonUtils.objectMapper();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    Map<String, String[]> parameterMap = new HashMap<>(req.getParameterMap());

    Map<String, String> headers = new HashMap<>();
    java.util.Enumeration<String> headerNames = req.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String name = headerNames.nextElement();
      headers.put(name, req.getHeader(name));
    }

    ApiModels.RequestInfo requestInfo = new ApiModels.RequestInfo(
        req.getMethod(),
        req.getRequestURI(),
        req.getContextPath(),
        req.getServletPath(),
        req.getQueryString(),
        parameterMap,
        headers,
        req.getRemoteAddr(),
        req.getRemotePort(),
        req.getProtocol(),
        req.getScheme()
    );

    Map<String, Object> data = new HashMap<>();
    data.put("request", requestInfo);
    data.put("attributes", getAttributes(req));
    data.put("parameters", getParameters(req));

    PrintWriter writer = resp.getWriter();
    objectMapper.writeValue(writer, new ApiModels.HttpResponse(200, "Request info retrieved", data));
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    Map<String, Object> data = new HashMap<>();
    data.put("method", req.getMethod());
    data.put("contentType", req.getContentType());
    data.put("contentLength", req.getContentLength());
    data.put("characterEncoding", req.getCharacterEncoding());

    // Read body
    StringBuilder body = new StringBuilder();
    String line;
    try (var reader = req.getReader()) {
      while ((line = reader.readLine()) != null) {
        body.append(line);
      }
    }
    data.put("body", body.toString());

    PrintWriter writer = resp.getWriter();
    objectMapper.writeValue(writer, new ApiModels.HttpResponse(200, "Request body retrieved", data));
  }

  private Map<String, Object> getAttributes(HttpServletRequest req) {
    Map<String, Object> attributes = new HashMap<>();
    java.util.Enumeration<String> attrNames = req.getAttributeNames();
    while (attrNames.hasMoreElements()) {
      String name = attrNames.nextElement();
      attributes.put(name, req.getAttribute(name));
    }
    return attributes;
  }

  private Map<String, String[]> getParameters(HttpServletRequest req) {
    Map<String, String[]> parameters = new HashMap<>();
    java.util.Enumeration<String> paramNames = req.getParameterNames();
    while (paramNames.hasMoreElements()) {
      String name = paramNames.nextElement();
      parameters.put(name, req.getParameterValues(name));
    }
    return parameters;
  }
}
