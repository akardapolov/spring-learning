package com.example.servlets.servlet;

import com.example.servlets.model.ApiModels;
import com.example.servlets.util.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.RequestDispatcher;
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
 * Servlet демонстрация Forward vs Redirect.
 */
@WebServlet(urlPatterns = {"/api/forward/*", "/api/redirect/*"}, loadOnStartup = 4)
public class ForwardRedirectServlet extends HttpServlet {

  private final ObjectMapper objectMapper = JsonUtils.objectMapper();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String servletPath = req.getServletPath();

    if (servletPath.startsWith("/api/forward")) {
      handleForward(req, resp);
    } else {
      handleRedirect(req, resp);
    }
  }

  private void handleForward(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String pathInfo = req.getPathInfo();

    if (pathInfo == null || pathInfo.equals("/")) {
      sendJsonResponse(resp, new ApiModels.HttpResponse(200, "Forward demo", Map.of(
          "description", "Forward happens on server, client doesn't know",
          "endpoints", Map.of(
              "/api/forward/to-welcome", "Forward to welcome",
              "/api/forward/with-attributes", "Forward with attributes",
              "/api/forward/chain", "Chain forward",
              "/api/forward/loop", "Loop protection demo"
          )
      )));
    } else if (pathInfo.equals("/to-welcome")) {
      req.setAttribute("forwardedFrom", req.getRequestURI());
      RequestDispatcher dispatcher = req.getRequestDispatcher("/welcome");
      try {
        dispatcher.forward(req, resp);
      } catch (ServletException e) {
        sendErrorResponse(resp, 500, "Internal Server Error", e.getMessage(), req.getRequestURI());
      }
    } else if (pathInfo.equals("/with-attributes")) {
      req.setAttribute("customAttr", "customValue");
      req.setAttribute("timestamp", System.currentTimeMillis());
      RequestDispatcher dispatcher = req.getRequestDispatcher("/api/request");
      try {
        dispatcher.forward(req, resp);
      } catch (ServletException e) {
        sendErrorResponse(resp, 500, "Internal Server Error", e.getMessage(), req.getRequestURI());
      }
    } else if (pathInfo.equals("/chain")) {
      req.setAttribute("step", 1);
      RequestDispatcher dispatcher = req.getRequestDispatcher("/api/forward/chain/step2");
      try {
        dispatcher.forward(req, resp);
      } catch (ServletException e) {
        sendErrorResponse(resp, 500, "Internal Server Error", e.getMessage(), req.getRequestURI());
      }
    } else if (pathInfo.equals("/chain/step2")) {
      Integer step = (Integer) req.getAttribute("step");
      if (step == null) step = 0;
      req.setAttribute("step", step + 1);
      sendJsonResponse(resp, new ApiModels.HttpResponse(200, "Chain step " + step, Map.of(
          "step", step,
          "final", true
      )));
    } else if (pathInfo.equals("/loop")) {
      Integer loopCount = (Integer) req.getAttribute("loopCount");
      if (loopCount == null) {
        loopCount = 0;
        req.setAttribute("loopCount", loopCount);
      }
      if (loopCount < 3) {
        req.setAttribute("loopCount", loopCount + 1);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/api/forward/loop");
        try {
          dispatcher.forward(req, resp);
          return;
        } catch (ServletException e) {
          sendErrorResponse(resp, 500, "Internal Server Error", e.getMessage(), req.getRequestURI());
        }
      }
      sendJsonResponse(resp, new ApiModels.HttpResponse(200, "Loop protection", Map.of(
          "loopCount", loopCount,
          "message", "Loop prevented"
      )));
    } else {
      sendErrorResponse(resp, 404, "Not Found", "Endpoint not found", req.getRequestURI());
    }
  }

  private void handleRedirect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String pathInfo = req.getPathInfo();

    if (pathInfo == null || pathInfo.equals("/")) {
      sendJsonResponse(resp, new ApiModels.HttpResponse(200, "Redirect demo", Map.of(
          "description", "Redirect happens on client, browser makes new request",
          "endpoints", Map.of(
              "/api/redirect/temporary", "Temporary redirect (302)",
              "/api/redirect/permanent", "Permanent redirect (301)",
              "/api/redirect/see-other", "See Other (303)"
          )
      )));
    } else if (pathInfo.equals("/temporary")) {
      resp.setStatus(HttpServletResponse.SC_FOUND);
      resp.setHeader("Location", "/api/demo");
      PrintWriter writer = resp.getWriter();
      writer.write("Redirecting to /api/demo");
    } else if (pathInfo.equals("/permanent")) {
      resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
      resp.setHeader("Location", "/api/demo");
      PrintWriter writer = resp.getWriter();
      writer.write("Moved permanently to /api/demo");
    } else if (pathInfo.equals("/see-other")) {
      resp.setStatus(HttpServletResponse.SC_SEE_OTHER);
      resp.setHeader("Location", "/api/demo");
      PrintWriter writer = resp.getWriter();
      writer.write("See Other at /api/demo");
    } else {
      sendErrorResponse(resp, 404, "Not Found", "Endpoint not found", req.getRequestURI());
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
