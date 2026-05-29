package com.example.broker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

  private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

  @JmsListener(destination = "demo-queue")
  public void receiveQueue(String message) {
    log.info("[QUEUE] Received message: {}", message);
  }

  @JmsListener(destination = "demo-topic", containerFactory = "topicFactory")
  public void receiveTopic(String message) {
    log.info("[TOPIC] Received message: {}", message);
  }
}
