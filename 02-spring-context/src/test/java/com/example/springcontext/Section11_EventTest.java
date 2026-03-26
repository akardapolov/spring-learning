package com.example.springcontext;

import com.example.springcontext.listener.CustomEventListener;
import com.example.springcontext.listener.EventPublisher;
import com.example.springcontext.model.ApiModels.EventResult;
import com.example.springcontext.service.SpringContextDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Раздел 11: Демонстрация обработки событий в Spring.
 * События позволяют реализовать pub-sub паттерн для слабосвязанной коммуникации.
 */
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section11_EventTest {

    private final SpringContextDemoService service;
    private final ApplicationContext context;
    private final EventPublisher eventPublisher;
    private final CustomEventListener eventListener;

    @Autowired
    Section11_EventTest(
            SpringContextDemoService service,
            ApplicationContext context,
            EventPublisher eventPublisher,
            CustomEventListener eventListener) {
        this.service = service;
        this.context = context;
        this.eventPublisher = eventPublisher;
        this.eventListener = eventListener;
    }

    @Test
    void shouldDemonstrateEvents() {
        EventResult result = service.eventDemo();

        // Проверка, что события обработаны
        assertThat(result.capturedEvents()).isNotEmpty();
        assertThat(result.customEventCount()).isGreaterThan(0);
    }

    @Test
    void shouldPublishAndReceiveEvent() {
        // Публикация и получение события
        int initialCount = eventListener.getCustomEventCount();

        eventPublisher.publishEvent("Test message");

        assertThat(eventListener.getCustomEventCount()).isEqualTo(initialCount + 1);
        assertThat(eventListener.getLastEventPayload()).contains("Test message");
    }

    @Test
    void shouldHaveEventListeners() {
        // Проверка наличия слушателей событий
        assertThat(context.containsBean("customEventListener")).isTrue();
        assertThat(context.containsBean("eventPublisher")).isTrue();
    }

    @Test
    void shouldReceiveContextRefreshedEvent() {
        // Контекст должен был опубликовать событие ContextRefreshedEvent
        var capturedEvents = eventListener.getCapturedEvents();
        assertThat(capturedEvents).anyMatch(event -> event.contains("CONTEXT_REFRESHED"));
    }

    @Test
    void shouldCaptureMultipleEvents() {
        // Публикация нескольких событий
        eventPublisher.publishEvent("Event 1");
        eventPublisher.publishEvent("Event 2");
        eventPublisher.publishEvent("Event 3");

        var capturedEvents = eventListener.getCapturedEvents();
        assertThat(capturedEvents).anyMatch(event -> event.contains("Event 1"));
        assertThat(capturedEvents).anyMatch(event -> event.contains("Event 2"));
        assertThat(capturedEvents).anyMatch(event -> event.contains("Event 3"));
    }
}
