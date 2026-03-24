package com.example.servlets.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

/**
 * Filter для логирования всех запросов.
 */
@WebFilter(
    urlPatterns = {"/*"},
    filterName = "loggingFilter",
    initParams = {
        @WebInitParam(name = "logHeaders", value = "true"),
        @WebInitParam(name = "logParams", value = "false")
    }
)
public class LoggingFilter implements Filter {

  private boolean logHeaders;
  private boolean logParams;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    logHeaders = Boolean.parseBoolean(filterConfig.getInitParameter("logHeaders"));
    logParams = Boolean.parseBoolean(filterConfig.getInitParameter("logParams"));
    System.out.println("[LoggingFilter] Initialized with logHeaders=" + logHeaders + ", logParams=" + logParams);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    Instant start = Instant.now();

    System.out.println("[LoggingFilter] >>> " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());

    try {
      chain.doFilter(request, response);
    } finally {
      long duration = Duration.between(start, Instant.now()).toMillis();
      System.out.println("[LoggingFilter] <<< " + httpRequest.getMethod() + " " + httpRequest.getRequestURI() + " - " + duration + "ms");
    }
  }

  @Override
  public void destroy() {
    System.out.println("[LoggingFilter] Destroyed");
  }
}
