package com.example.broker.controller;

import com.example.broker.model.MessageRequest;
import com.example.broker.service.MessageProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

  private final MessageProducer messageProducer;

  public MessageController(MessageProducer messageProducer) {
    this.messageProducer = messageProducer;
  }

  @PostMapping("/queue")
  public ResponseEntity<Map<String, String>> sendToQueue(@RequestBody MessageRequest request) {
    messageProducer.sendToQueue("demo-queue", request.content());
    return ResponseEntity.ok(Map.of("status", "sent", "destination", "demo-queue", "message", request.content()));
  }

  @PostMapping("/topic")
  public ResponseEntity<Map<String, String>> sendToTopic(@RequestBody MessageRequest request) {
    messageProducer.sendToTopic("demo-topic", request.content());
    return ResponseEntity.ok(Map.of("status", "sent", "destination", "demo-topic", "message", request.content()));
  }

  @GetMapping("/status")
  public ResponseEntity<Map<String, Object>> status() {
    return ResponseEntity.ok(Map.of(
        "broker", "ActiveMQ Classic",
        "mode", "embedded",
        "status", "UP"
    ));
  }

  @GetMapping("/info")
  public ResponseEntity<Map<String, Object>> info() {
    return ResponseEntity.ok(Map.of(
        "broker", "ActiveMQ Classic",
        "mode", "embedded",
        "jmsPort", 61616,
        "queues", new String[]{"demo-queue"},
        "topics", new String[]{"demo-topic"}
    ));
  }
}
