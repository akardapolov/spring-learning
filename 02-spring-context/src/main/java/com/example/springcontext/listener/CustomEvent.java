package com.example.springcontext.listener;

import org.springframework.context.ApplicationEvent;

/**
 * Кастомное событие Spring для демонстрации обработки событий.
 */
public class CustomEvent extends ApplicationEvent {

    private final String message;
    private final long eventTimestamp;

    public CustomEvent(Object source, String message) {
        super(source);
        this.message = message;
        this.eventTimestamp = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }

    public long getEventTimestamp() {
        return eventTimestamp;
    }
}
