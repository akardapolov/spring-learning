package com.example.springsecurity.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SecurityEventsCollector {

  private final List<SecurityEvent> events = new ArrayList<>();

  public void recordEvent(String eventType, String username, String details) {
    events.add(new SecurityEvent(
        eventType,
        LocalDateTime.now(),
        username,
        details
    ));
  }

  public List<SecurityEvent> getEvents() {
    return new ArrayList<>(events);
  }

  public void clear() {
    events.clear();
  }

  public record SecurityEvent(String eventType, LocalDateTime timestamp, String username, String details) {
  }
}
