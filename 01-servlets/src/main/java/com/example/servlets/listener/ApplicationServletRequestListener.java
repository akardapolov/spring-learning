package com.example.servlets.listener;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

/**
 * ServletRequestListener для логирования жизненного цикла запросов.
 */
@WebListener
public class ApplicationServletRequestListener implements ServletRequestListener {

  @Override
  public void requestInitialized(ServletRequestEvent sre) {
    HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
    request.setAttribute("requestStartTime", System.currentTimeMillis());
  }

  @Override
  public void requestDestroyed(ServletRequestEvent sre) {
    HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
    Long startTime = (Long) request.getAttribute("requestStartTime");
    if (startTime != null) {
      long duration = System.currentTimeMillis() - startTime;
      // System.out.println("[ServletRequestListener] Request completed in " + duration + "ms");
    }
  }
}
