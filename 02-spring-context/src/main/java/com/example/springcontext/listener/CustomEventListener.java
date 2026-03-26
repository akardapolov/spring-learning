package com.example.springcontext.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Слушатель событий для демонстрации Spring Events.
 */
@Component
public class CustomEventListener {

    private static final Logger log = LoggerFactory.getLogger(CustomEventListener.class);

    private final List<String> capturedEvents = new ArrayList<>();
    private int customEventCount = 0;

    @EventListener
    public void handleCustomEvent(CustomEvent event) {
        log.info("Received custom event: {}", event.getMessage());
        capturedEvents.add("CUSTOM: " + event.getMessage());
        customEventCount++;
    }

    @EventListener
    public void handleContextRefreshedEvent(ContextRefreshedEvent event) {
        log.info("ApplicationContext refreshed");
        capturedEvents.add("CONTEXT_REFRESHED");
    }

    public List<String> getCapturedEvents() {
        return new ArrayList<>(capturedEvents);
    }

    public int getCustomEventCount() {
        return customEventCount;
    }

    public String getLastEventPayload() {
        if (capturedEvents.isEmpty()) {
            return null;
        }
        return capturedEvents.getLast();
    }
}
