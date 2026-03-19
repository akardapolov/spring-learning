package com.example.springdatajdbc.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LifecycleEventsCollector {

  private final List<String> events = new ArrayList<>();

  public void add(String event) {
    events.add(event);
  }

  public List<String> snapshot() {
    return new ArrayList<>(events);
  }

  public void clear() {
    events.clear();
  }
}
