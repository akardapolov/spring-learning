package com.example.broker.config;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class JmsConfig {

  public static final String TOPIC_CONTAINER_FACTORY = "topicFactory";

  @Bean(initMethod = "start", destroyMethod = "stop")
  @ConditionalOnProperty(prefix = "spring.activemq", name = "in-memory", havingValue = "false", matchIfMissing = true)
  public BrokerService brokerService() throws Exception {
    BrokerService broker = new BrokerService();
    broker.setPersistent(false);
    broker.setUseJmx(false);
    broker.setBrokerName("embedded-broker");

    TransportConnector tcp = new TransportConnector();
    tcp.setUri(new java.net.URI("tcp://localhost:61616"));
    broker.addConnector(tcp);

    return broker;
  }

  @Bean
  public JmsListenerContainerFactory<?> topicFactory(
      org.springframework.jms.connection.CachingConnectionFactory connectionFactory) {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setPubSubDomain(true);
    factory.setMessageConverter(jacksonJmsMessageConverter());
    return factory;
  }

  @Bean
  public MessageConverter jacksonJmsMessageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }
}
