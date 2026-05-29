package com.example.broker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class JmsIntegrationTest {

  @Autowired
  private JmsTemplate jmsTemplate;

  @Test
  void queue_sendAndReceive_shouldDeliverMessage() {
    jmsTemplate.setReceiveTimeout(3000);
    String message = "integration-test-queue";

    jmsTemplate.convertAndSend("test-queue", message);

    Object received = jmsTemplate.receiveAndConvert("test-queue");

    assertNotNull(received);
    assertEquals(message, received);
  }

  @Test
  void topic_send_shouldNotThrow() {
    jmsTemplate.setReceiveTimeout(3000);
    jmsTemplate.setPubSubDomain(true);
    try {
      assertDoesNotThrow(() -> jmsTemplate.convertAndSend("test-topic", "integration-test-topic"));
    } finally {
      jmsTemplate.setPubSubDomain(false);
    }
  }
}
