# Spring Boot Demo

Демо‑проект на Spring Boot, демонстрирующий основные концепции фреймворка.

## Требования

- JDK 21+
- Maven 3.9+

Проверка:
```bash
java -version
mvn -v
```

## Запуск приложения локально

Из корня проекта:

### Вариант 1. Через Maven

```bash
mvn clean spring-boot:run -pl 03-spring-boot
```

### Вариант 2. Через собранный JAR

```bash
mvn clean package -pl 03-spring-boot
java -jar 03-spring-boot/target/03-spring-boot-1.0.0.jar
```

Приложение стартует на порту, указанном в `application.yaml`:

- Dev: `http://localhost:8080`
- Prod: `http://localhost:8081`

## Профили

Проект поддерживает три профиля:

- `dev` (по умолчанию) — режим разработки
- `prod` — рабочий режим
- `test` — тестовый режим

Запуск с конкретным профилем:
```bash
mvn spring-boot:run -pl 03-spring-boot -Dspring-boot.run.profiles=prod
```

или
```bash
java -jar 03-spring-boot/target/03-spring-boot-1.0.0.jar --spring.profiles.active=prod
```

## Основные endpoint'ы

### Application Info
- `GET /api/demo/app-info` — информация о приложении
- `GET /api/demo/welcome` — приветственное сообщение
- `GET /api/demo/greeting?name=World` — персональное приветствие

### Bean Inspection
- `GET /api/demo/beans` — список всех бинов (ограничен первыми 50)
- `GET /api/demo/beans/{beanName}` — информация о конкретном бине
- `GET /api/demo/beans/count` — общее количество бинов

### Configuration
- `GET /api/demo/config/property?key=app.name` — значение конкретного свойства
- `GET /api/demo/config/sources` — источники конфигурации
- `GET /api/demo/config/app` — конфигурация приложения

### Profiles
- `GET /api/demo/profiles` — информация про профилях
- `GET /api/demo/profiles/current` — текущий профиль
- `GET /api/demo/profiles/message` — сообщение для текущего профиля

### Conditional Beans
- `GET /api/demo/conditional/message` — сообщение условного бина
- `GET /api/demo/conditional/feature/{feature}` — проверка включенности фичи (cache, metrics, logging, debug)

### Health Check
- `GET /api/demo/health/custom` — кастомная проверка здоровья

## Actuator

Actuator endpoints доступны по пути `/actuator`:

- `/actuator/health` — состояние приложения
- `/actuator/info` — информация о приложении
- `/actuator/metrics` — метрики приложения
- `/actuator/env` — окружение и свойства
- `/actuator/beans` — бины контекста
- `/actuator/configprops` — конфигурационные свойства
- `/actuator/loggers` — настройки логирования

## Что демонстрирует проект

### Section 1: Application Startup
- `@SpringBootApplication` мета-аннотация
- Загрузка контекста Spring
- Основные аннотации Spring Boot

### Section 2: Configuration Properties
- `@ConfigurationProperties` для типобезопасной привязки конфигурации
- Вложенные свойства конфигурации
- Значения по умолчанию
- Валидация конфигурационных свойств

### Section 3: Profiles
- Профили Spring
- Профиль-специфичная конфигурация
- Активные профили
- Условная логика на основе профилей

### Section 4: Bean Inspection
- Доступ к `ApplicationContext`
- Инспекция бинов
- Понимание областей видимости (scopes)
- Singleton vs Prototype бины

### Section 5: Conditional Beans
- `@ConditionalOnProperty`
- `@ConditionalOnMissingBean`
- Понимание принципов авто-конфигурации
- Условное создание бинов

### Section 6: Dependency Injection
- Конструкторная инъекция (предпочтительный подход)
- `@Autowired` аннотация
- Инъекция `ApplicationContext`
- `@Qualifier` для выбора бина

### Section 7: Environment
- Доступ к `Environment`
- Разрешение свойств (properties)
- Источники конфигурации
- Приоритет множественных источников

### Section 8: REST Controllers
- `@RestController` аннотация
- `@GetMapping`, `@PostMapping` и другие методы HTTP
- Параметры запроса и переменные пути
- Обработка ответов

### Section 9: Exception Handling
- Кастомные исключения
- Выбрасывание исключений в сервисах
- `@RestControllerAdvice` для централизованной обработки
- Обработка исключений валидации

## Структура проекта

```text
src
├── main
│   ├── java/com/example/springboot
│   │   ├── SpringBootDemoApplication.java
│   │   ├── config
│   │   │   ├── ConditionalConfig.java
│   │   │   ├── CustomMetricsConfig.java
│   │   │   ├── DemoDataInitializer.java
│   │   │   └── WebConfig.java
│   │   ├── controller
│   │   │   ├── DemoController.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── exception
│   │   │   ├── AppException.java
│   │   │   ├── BeanNotFoundException.java
│   │   │   └── ConfigurationException.java
│   │   ├── model
│   │   │   ├── ApiModels.java
│   │   │   └── AppProperties.java
│   │   └── service
│   │       ├── AppInfoService.java
│   │       ├── BeanService.java
│   │       ├── ConditionalBeanService.java
│   │       ├── ConfigurationService.java
│   │       └── ProfileService.java
│   └── resources
│       └── application.yaml
└── test
    └── java/com/example/springboot
        ├── Section1_ApplicationStartupTest.java
        ├── Section2_ConfigurationPropertiesTest.java
        ├── Section3_ProfilesTest.java
        ├── Section4_BeanInspectionTest.java
        ├── Section5_ConditionalBeansTest.java
        ├── Section6_DependencyInjectionTest.java
        ├── Section7_EnvironmentTest.java
        ├── Section8_RestControllerTest.java
        └── Section9_ExceptionHandlingTest.java
```

## Тесты

Запуск всех тестов:

```bash
mvn test -pl 03-spring-boot
```

Проект содержит интеграционные тесты для:

- запуска приложения
- конфигурационных свойств
- профилей
- инспекции бинов
- условных бинов
- инъекции зависимостей
- окружения и конфигурации
- REST контроллеров
- обработки исключений

## Основные концепции Spring Boot

### Auto-Configuration
Spring Boot автоматически конфигурирует приложение на основе classpath. Можно просмотреть отчёт о авто-конфигурации:
```bash
mvn spring-boot:run -pl 03-spring-boot -Ddebug
```

### Configuration Properties
Используйте `@ConfigurationProperties` для типобезопасной привязки конфигурации:
```java
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String name;
    private String version;
    // ...
}
```

### Conditional Beans
Создавайте бины условно с помощью аннотаций `@ConditionalOn...`:
```java
@Bean
@ConditionalOnProperty(name = "feature.enabled", havingValue = "true")
public FeatureService featureService() {
    return new FeatureService();
}
```

### Profiles
Используйте профили для различных окружений:
```yaml
---
spring:
  config:
    activate:
      on-profile: dev
app:
  environment: development
```

### Actuator
Используйте Actuator для мониторинга и управления приложением:
- Health checks
- Metrics
- Info
- Environment
- Beans

## Сравнение DI подходов

| Подход                | Суть                                     | Когда использовать                      |
|-----------------------|------------------------------------------|-----------------------------------------|
| Constructor Injection | Зависимости через конструктор            | Всегда — это рекомендуемый способ       |
| Field Injection       | @Autowired над полем                     | Только в тестах или специфичных случаях |
| Setter Injection      | @Autowired над сеттером                  | Для опциональных зависимостей           |
| ApplicationContext    | Получение бина из контекста              | Для динамического поиска бинов          |
| @Qualifier            | Уточнение имени бина при конфликте типов | Когда есть несколько бинов одного типа  |
| @Primary              | Помечение основного бина                 | Когда есть несколько бинов одного типа  |
