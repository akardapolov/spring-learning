package com.example.broker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MessageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void sendToQueue_shouldReturnSentStatus() throws Exception {
    mockMvc.perform(post("/api/messages/queue")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"content\":\"test-queue-message\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("sent"))
        .andExpect(jsonPath("$.destination").value("demo-queue"))
        .andExpect(jsonPath("$.message").value("test-queue-message"));
  }

  @Test
  void sendToTopic_shouldReturnSentStatus() throws Exception {
    mockMvc.perform(post("/api/messages/topic")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"content\":\"test-topic-message\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("sent"))
        .andExpect(jsonPath("$.destination").value("demo-topic"));
  }

  @Test
  void status_shouldReturnUp() throws Exception {
    mockMvc.perform(get("/api/messages/status"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.broker").value("ActiveMQ Classic"))
        .andExpect(jsonPath("$.status").value("UP"))
        .andExpect(jsonPath("$.mode").value("embedded"));
  }

  @Test
  void info_shouldReturnBrokerDetails() throws Exception {
    mockMvc.perform(get("/api/messages/info"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.broker").value("ActiveMQ Classic"))
        .andExpect(jsonPath("$.jmsPort").value(61616))
        .andExpect(jsonPath("$.queues[0]").value("demo-queue"))
        .andExpect(jsonPath("$.topics[0]").value("demo-topic"));
  }
}
