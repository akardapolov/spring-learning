# Message Broker Demo (ActiveMQ Classic)

Демо-проект на Spring Boot + ActiveMQ Classic (embedded).

---

## Оглавление

1. [Требования](#требования)
2. [Запуск приложения локально](#запуск-приложения-локально)
3. [Порты](#порты)
4. [Что демонстрирует проект](#что-демонстрирует-проект)
5. [Основные endpoint'ы](#основные-endpointы)
6. [Структура проекта](#структура-проекта)
7. [Тесты](#тесты)
8. [Варианты проверки](#варианты-проверки)

---

## Требования

- JDK 21+
- Maven 3.9+

Проверка:
```bash
java -version
mvn -v
```

---

## Запуск приложения локально

Из корня проекта:

### Вариант 1. Через Maven

```bash
mvn -pl 09-broker spring-boot:run
```

### Вариант 2. Через собранный JAR

```bash
mvn -pl 09-broker clean package
java -jar 09-broker/target/09-broker-1.0.0.jar
```

Приложение стартует на порту, указанном в `application.yaml`:

- `http://localhost:8893`

---

## Порты

| Сервис | Порт | Описание |
|--------|------|----------|
| HTTP (Spring Boot) | 8893 | REST API |
| JMS (ActiveMQ) | 61616 | Брокер сообщений (TCP) |

---

## Что демонстрирует проект

Проект показывает основные возможности работы с брокером сообщений:

- embedded-режим ActiveMQ Classic (брокер внутри JVM)
- отправка сообщений в Queue (point-to-point)
- отправка сообщений в Topic (pub/sub)
- Spring JMS: `JmsTemplate` и `@JmsListener`
- Jakarta JMS API (Spring Boot 3.x)
- REST endpoints для отправки сообщений
- TCP-доступ к embedded брокеру для внешних сервисов

---

## Основные endpoint'ы

### Отправка сообщений

- `POST /api/messages/queue` — отправить сообщение в очередь
- `POST /api/messages/topic` — отправить сообщение в топик

### Информация

- `GET /api/messages/status` — статус брокера
- `GET /api/messages/info` — информация о брокере

---

## Структура проекта

```text
src
├── main
│   ├── java/com/example/broker
│   │   ├── config
│   │   │   └── JmsConfig.java
│   │   ├── controller
│   │   │   └── MessageController.java
│   │   ├── exception
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── model
│   │   │   └── MessageRequest.java
│   │   ├── service
│   │   │   ├── MessageConsumer.java
│   │   │   └── MessageProducer.java
│   │   └── BrokerApplication.java
│   └── resources
│       ├── static
│       │   └── index.html
│       └── application.yaml
└── test
    └── java/com/example/broker
        └── BrokerApplicationTest.java
```

---

## Тесты

Запуск всех тестов:

```bash
mvn -pl 09-broker test
```

---

## Варианты проверки

### 1. Отправка в очередь
```bash
curl -X POST http://localhost:8893/api/messages/queue \
  -H "Content-Type: application/json" \
  -d '{"content":"hello queue"}'
```

### 2. Отправка в топик
```bash
curl -X POST http://localhost:8893/api/messages/topic \
  -H "Content-Type: application/json" \
  -d '{"content":"hello topic"}'
```

### 3. Проверка статуса брокера
```bash
curl http://localhost:8893/api/messages/status
```

### 4. Информация о брокере
```bash
curl http://localhost:8893/api/messages/info
```

### 5. Подключение внешнего клиента

Брокер доступен по TCP для внешних сервисов:

```bash
telnet localhost 61616
```

**Как подключиться из другого Spring Boot модуля:**

Добавить зависимость:
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-activemq</artifactId>
</dependency>
```

Настроить `application.yaml`:
```yaml
spring:
  activemq:
    broker-url: tcp://localhost:61616
```

Использовать `JmsTemplate` и `@JmsListener`:
```java
@Service
public class ExternalProducer {
  private final JmsTemplate jmsTemplate;

  public void send(String message) {
    jmsTemplate.convertAndSend("demo-queue", message);
  }
}

@Component
public class ExternalConsumer {
  @JmsListener(destination = "demo-queue")
  public void receive(String message) {
    // обработка сообщения
  }
}
```
