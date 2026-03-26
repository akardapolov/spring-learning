package com.example.springcontext.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Публикатор событий для демонстрации Spring Events.
 */
@Component
public class EventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public EventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishEvent(String message) {
        CustomEvent event = new CustomEvent(this, message);
        eventPublisher.publishEvent(event);
    }
}
