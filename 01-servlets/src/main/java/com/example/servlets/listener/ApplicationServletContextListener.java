package com.example.servlets.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.time.Instant;

/**
 * ServletContextListener для логирования событий жизненного цикла контекста приложения.
 */
@WebListener
public class ApplicationServletContextListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ServletContext context = sce.getServletContext();
    context.setAttribute("appStartTime", Instant.now());

    System.out.println("[ServletContextListener] Application context initialized");
    System.out.println("[ServletContextListener] Context path: " + context.getContextPath());
    System.out.println("[ServletContextListener] Server info: " + context.getServerInfo());
    System.out.println("[ServletContextListener] Servlet API version: " + context.getEffectiveMajorVersion() + "." + context.getEffectiveMinorVersion());
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    System.out.println("[ServletContextListener] Application context destroyed");
  }
}
