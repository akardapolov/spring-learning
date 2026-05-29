package com.example.broker.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

  private final JmsTemplate jmsTemplate;

  public MessageProducer(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  public void sendToQueue(String destination, String message) {
    jmsTemplate.convertAndSend(destination, message);
  }

  public void sendToTopic(String destination, String message) {
    jmsTemplate.setPubSubDomain(true);
    try {
      jmsTemplate.convertAndSend(destination, message);
    } finally {
      jmsTemplate.setPubSubDomain(false);
    }
  }
}
