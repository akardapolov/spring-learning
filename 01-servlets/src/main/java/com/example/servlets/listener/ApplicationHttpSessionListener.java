package com.example.servlets.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * HttpSessionListener для подсчета активных сессий.
 */
@WebListener
public class ApplicationHttpSessionListener implements HttpSessionListener {

  private static final AtomicInteger activeSessions = new AtomicInteger(0);

  @Override
  public void sessionCreated(HttpSessionEvent se) {
    int count = activeSessions.incrementAndGet();
    System.out.println("[HttpSessionListener] Session created: " + se.getSession().getId() + " (Active: " + count + ")");
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent se) {
    int count = activeSessions.decrementAndGet();
    System.out.println("[HttpSessionListener] Session destroyed: " + se.getSession().getId() + " (Active: " + count + ")");
  }

  public static int getActiveSessionCount() {
    return activeSessions.get();
  }
}
