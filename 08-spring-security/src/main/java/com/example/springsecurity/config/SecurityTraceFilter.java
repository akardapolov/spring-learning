package com.example.springsecurity.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityTraceFilter extends OncePerRequestFilter {

  private static final Logger log = LoggerFactory.getLogger(SecurityTraceFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    log.info(">>> REQUEST {} {}", request.getMethod(), request.getRequestURI());
    printAuth("BEFORE");

    filterChain.doFilter(request, response);

    printAuth("AFTER");
    log.info("<<< RESPONSE status={}", response.getStatus());
  }

  private void printAuth(String stage) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null) {
      log.info("[{}] Authentication = null", stage);
      return;
    }

    log.info("[{}] authClass={}, name={}, authenticated={}, authorities={}",
             stage,
             auth.getClass().getSimpleName(),
             auth.getName(),
             auth.isAuthenticated(),
             auth.getAuthorities());
  }
}