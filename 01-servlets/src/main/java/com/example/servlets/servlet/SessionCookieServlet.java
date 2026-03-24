package com.example.servlets.servlet;

import com.example.servlets.model.ApiModels;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet демонстрация Session и Cookie API.
 */
@WebServlet(urlPatterns = "/api/session/*", loadOnStartup = 3)
public class SessionCookieServlet extends HttpServlet {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String pathInfo = req.getPathInfo();

    if (pathInfo == null || pathInfo.equals("/")) {
      handleSessionInfo(req, resp);
    } else if (pathInfo.equals("/create")) {
      handleCreateSession(req, resp);
    } else if (pathInfo.equals("/invalidate")) {
      handleInvalidateSession(req, resp);
    } else if (pathInfo.equals("/attributes")) {
      handleAttributes(req, resp);
    } else if (pathInfo.equals("/cookies")) {
      handleCookies(req, resp);
    } else {
      sendErrorResponse(resp, 404, "Not Found", "Endpoint not found", req.getRequestURI());
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String pathInfo = req.getPathInfo();

    if (pathInfo == null || pathInfo.equals("/")) {
      handleSetAttribute(req, resp);
    } else {
      sendErrorResponse(resp, 404, "Not Found", "Endpoint not found", req.getRequestURI());
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String key = req.getParameter("key");
    if (key != null) {
      handleRemoveAttribute(req, resp, key);
    } else {
      sendErrorResponse(resp, 400, "Bad Request", "Key parameter required", req.getRequestURI());
    }
  }

  private void handleSessionInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    HttpSession session = req.getSession(false);

    Map<String, Object> data = new HashMap<>();
    if (session == null) {
      data.put("session", null);
      data.put("message", "No active session");
    } else {
      Map<String, Object> sessionAttrs = new HashMap<>();
      java.util.Enumeration<String> attrNames = session.getAttributeNames();
      while (attrNames.hasMoreElements()) {
        String name = attrNames.nextElement();
        sessionAttrs.put(name, session.getAttribute(name));
      }

      ApiModels.SessionInfo sessionInfo = new ApiModels.SessionInfo(
          session.getId(),
          session.isNew(),
          session.getMaxInactiveInterval(),
          session.getCreationTime(),
          session.getLastAccessedTime(),
          sessionAttrs
      );
      data.put("session", sessionInfo);
    }

    data.put("cookies", getCookies(req));
    sendJsonResponse(resp, new ApiModels.HttpResponse(200, "Session info", data));
  }

  private void handleCreateSession(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    HttpSession session = req.getSession(true);
    session.setAttribute("createdAt", System.currentTimeMillis());
    session.setAttribute("userAgent", req.getHeader("User-Agent"));

    // Create a cookie
    Cookie cookie = new Cookie("sessionId", session.getId());
    cookie.setMaxAge(1800); // 30 minutes
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    resp.addCookie(cookie);

    sendJsonResponse(resp, new ApiModels.HttpResponse(200, "Session created", Map.of(
        "sessionId", session.getId(),
        "isNew", session.isNew(),
        "cookieCreated", true
    )));
  }

  private void handleInvalidateSession(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    HttpSession session = req.getSession(false);
    if (session != null) {
      session.invalidate();
    }

    // Remove cookie
    Cookie cookie = new Cookie("sessionId", "");
    cookie.setMaxAge(0);
    cookie.setPath("/");
    resp.addCookie(cookie);

    sendJsonResponse(resp, new ApiModels.HttpResponse(200, "Session invalidated", Map.of(
        "message", "Session and cookie removed"
    )));
  }

  private void handleAttributes(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    HttpSession session = req.getSession(false);
    Map<String, Object> attributes = new HashMap<>();

    if (session != null) {
      java.util.Enumeration<String> attrNames = session.getAttributeNames();
      while (attrNames.hasMoreElements()) {
        String name = attrNames.nextElement();
        attributes.put(name, session.getAttribute(name));
      }
    }

    sendJsonResponse(resp, new ApiModels.HttpResponse(200, "Session attributes", attributes));
  }

  private void handleCookies(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    List<ApiModels.CookieInfo> cookieInfos = getCookies(req);
    sendJsonResponse(resp, new ApiModels.HttpResponse(200, "Cookies info", cookieInfos));
  }

  private void handleSetAttribute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    HttpSession session = req.getSession(true);
    Map<String, Object> body = objectMapper.readValue(req.getInputStream(), Map.class);

    for (Map.Entry<String, Object> entry : body.entrySet()) {
      session.setAttribute(entry.getKey(), entry.getValue());
    }

    sendJsonResponse(resp, new ApiModels.HttpResponse(200, "Attributes set", body));
  }

  private void handleRemoveAttribute(HttpServletRequest req, HttpServletResponse resp, String key) throws IOException {
    HttpSession session = req.getSession(false);
    if (session != null) {
      Object value = session.getAttribute(key);
      session.removeAttribute(key);
      sendJsonResponse(resp, new ApiModels.HttpResponse(200, "Attribute removed", Map.of(
          "key", key,
          "value", value
      )));
    } else {
      sendErrorResponse(resp, 404, "Not Found", "No active session", req.getRequestURI());
    }
  }

  private List<ApiModels.CookieInfo> getCookies(HttpServletRequest req) {
    List<ApiModels.CookieInfo> cookieInfos = new ArrayList<>();
    Cookie[] cookies = req.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        cookieInfos.add(new ApiModels.CookieInfo(
            cookie.getName(),
            cookie.getValue(),
            cookie.getDomain(),
            cookie.getPath(),
            cookie.getMaxAge(),
            cookie.getSecure(),
            cookie.isHttpOnly()
        ));
      }
    }

    return cookieInfos;
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
