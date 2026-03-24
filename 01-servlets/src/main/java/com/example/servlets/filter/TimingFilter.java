package com.example.servlets.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

/**
 * Filter для измерения времени выполнения запросов к API.
 */
@WebFilter(
    urlPatterns = {"/api/*"},
    filterName = "timingFilter"
)
public class TimingFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    System.out.println("[TimingFilter] Initialized");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    Instant start = Instant.now();

    try {
      chain.doFilter(request, response);
    } finally {
      long duration = Duration.between(start, Instant.now()).toMillis();
      if (duration > 100) {
        System.out.println("[TimingFilter] SLOW REQUEST: " + httpRequest.getRequestURI() + " - " + duration + "ms");
      }
    }
  }

  @Override
  public void destroy() {
    System.out.println("[TimingFilter] Destroyed");
  }
}
