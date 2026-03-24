package com.example.servlets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Main Spring Boot application class for Servlets Demo.
 * @ServletComponentScan enables scanning for @WebServlet, @WebFilter, @WebListener annotations.
 */
@SpringBootApplication
@ServletComponentScan
public class ServletsApplication {
  public static void main(String[] args) {
    SpringApplication.run(ServletsApplication.class, args);
  }
}
