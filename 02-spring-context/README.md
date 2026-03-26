# Spring Context Demo

Демо‑проект на Spring Core / IoC (Inversion of Control).

## Требования

- JDK 21+
- Maven 3.9+

Проверка:
```bash
java -version
mvn -v
```

## Запуск тестов

Из корня проекта:

```bash
cd 02-spring-context
mvn test
```

Запуск конкретного теста:
```bash
mvn test -Dtest=Section1_IoCContainerTest
```

## Что демонстрирует проект

Проект показывает основные возможности Spring Core / IoC контейнера:

### Раздел 1: IoC Container
- Инверсия управления (Inversion of Control)
- ApplicationContext как контейнер бинов
- Регистрация и получение бинов из контекста

### Раздел 2: Dependency Injection
- Конструкторная инъекция (рекомендуемый подход)
- Setter инъекция
- Field инъекция (не рекомендуется, но демонстрируется)
- @Autowired аннотация

### Раздел 3: Scope
- Singleton scope (по умолчанию)
- Prototype scope
- Различия в поведении между singleton и prototype

### Раздел 4: Lifecycle
- Жизненный цикл бина
- @PostConstruct и @PreDestroy
- Порядок вызова событий жизненного цикла

### Раздел 5: Configuration
- @Configuration классы
- @Bean методы
- Декларативное определение бинов и зависимостей
- @Primary для выбора бина по умолчанию

### Раздел 6: Stereotypes
- @Component - базовая аннотация для компонентов
- @Service - для сервисного слоя
- @Repository - для репозиториев (доступ к данным)
- @Controller - для MVC контроллеров (в веб-слое)

### Раздел 7: @Autowired и @Qualifier
- Автоматическая инъекция зависимостей
- Выбор конкретного бина при наличии нескольких кандидатов
- Получение бина по имени

### Раздел 8: Property Injection
- @Value для инъекции значений свойств
- Использование значений по умолчанию
- Environment API для доступа к конфигурации
- Работа с профилями

### Раздел 9: ApplicationContextAware
- Получение доступа к ApplicationContext из бина
- Программная работа с бинами
- Проверка существования бинов

### Раздел 10: Profiles
- Различные конфигурации для разных окружений
- @Profile для профилизации бинов
- Активация профилей через свойства

### Раздел 11: Events
- Публикация и подписка на события
- @EventListener для обработки событий
- Стандартные события Spring (ContextRefreshedEvent и др.)
- Кастомные события

### Раздел 12: Conditional Beans
- @ConditionalOnProperty
- @ConditionalOnClass
- @ConditionalOnMissingBean
- @ConditionalOnProfile

## Структура проекта

```text
src
├── main
│   ├── java/com/example/springcontext
│   │   ├── config
│   │   │   ├── AppConfig.java              # Конфигурация через @Bean
│   │   │   ├── LifecycleBean.java         # Демонстрация жизненного цикла
│   │   │   ├── ProfileConfig.java         # Конфигурация профилей
│   │   │   └── ConditionalConfig.java     # Условные бины
│   │   ├── entity
│   │   │   ├── Product.java
│   │   │   ├── Category.java
│   │   │   ├── Customer.java
│   │   │   ├── Order.java
│   │   │   ├── OrderItem.java
│   │   │   └── OrderStatus.java
│   │   ├── listener
│   │   │   ├── CustomEvent.java           # Кастомное событие
│   │   │   ├── CustomEventListener.java   # Слушатель событий
│   │   │   ├── EventPublisher.java        # Публикатор событий
│   │   │   └── ContextAwareBean.java      # ApplicationContextAware пример
│   │   ├── model
│   │   │   └── ApiModels.java             # DTO для ответов
│   │   ├── repository
│   │   │   ├── ProductRepository.java     # @Repository пример
│   │   │   └── CategoryRepository.java    # @Repository пример
│   │   └── service
│   │       ├── ProductService.java        # @Service пример
│   │       ├── CategoryService.java       # @Service пример
│   │       ├── OrderService.java          # @Service пример
│   │       ├── PrototypeProductService.java # Prototype scope пример
│   │       └── SpringContextDemoService.java # Сервис для демо
│   └── resources
└── test
    └── java/com/example/springcontext
        ├── Section1_IoCContainerTest.java
        ├── Section2_DependencyInjectionTest.java
        ├── Section3_ScopeTest.java
        ├── Section4_LifecycleTest.java
        ├── Section5_ConfigurationTest.java
        ├── Section6_StereotypesTest.java
        ├── Section7_AutowiredQualifierTest.java
        ├── Section8_PropertyInjectionTest.java
        ├── Section9_ContextAwareTest.java
        ├── Section10_ProfileTest.java
        ├── Section11_EventTest.java
        └── Section12_ConditionalTest.java
```

## Основные концепции Spring IoC

### IoC (Inversion of Control)
IoC - это паттерн проектирования, при котором управление жизненным циклом объектов и их зависимостями передается фреймворку (контейнеру Spring). Вместо того чтобы объект сам создавал свои зависимости, контейнер Spring предоставляет их.

### Dependency Injection (DI)
DI - это реализация IoC, при которой зависимости внедряются в объект. Основные типы инъекции:
1. **Конструкторная инъекция** - зависимости передаются через конструктор (рекомендуется)
2. **Setter инъекция** - зависимости устанавливаются через сеттеры
3. **Field инъекция** - зависимости внедряются прямо в поля (не рекомендуется)

### Bean Scopes
- **singleton** - один экземпляр на контейнер (по умолчанию)
- **prototype** - новый экземпляр при каждом запросе
- **request** - один экземпляр на HTTP запрос (веб)
- **session** - один экземпляр на HTTP сессию (веб)
- **application** - один экземпляр на ServletContext (веб)

### Литература и дополнительные источники

1. **Официальная документация Spring**
   - [Spring Framework Documentation](https://docs.spring.io/spring-framework/reference/core/index.html)
   - [Spring IoC Container](https://docs.spring.io/spring-framework/reference/core/beans/introduction.html)

2. **Книги**
   - "Spring in Action" by Craig Walls
   - "Spring Boot in Action" by Craig Walls
   - "Pro Spring 5" by Chris Schaefer

3. **Онлайн-ресурсы**
   - [Baeldung - Spring Core](https://www.baeldung.com/spring-core)
   - [Spring Guides](https://spring.io/guides)
   - [Spring.io Blog](https://spring.io/blog)

## Советы по использованию

### Предпочитайте конструкторную инъекцию
```java
// Хорошо
@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
}

// Плохо
@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
}
```

### Используйте final поля для неизменяемых зависимостей
```java
public class ProductService {
    private final ProductRepository repository; // final гарантирует инъекцию
    // ...
}
```

### Применяйте @Primary для выбора бина по умолчанию
```java
@Bean
@Primary
public Product primaryProduct() {
    return new Product("Primary", "DEFAULT", BigDecimal.ONE);
}
```

### Используйте @Profile для профилизации
```java
@Bean
@Profile("dev")
public DataSource devDataSource() {
    // конфигурация для разработки
}

@Bean
@Profile("prod")
public DataSource prodDataSource() {
    // конфигурация для продакшена
}
```

## Тестирование

Проект содержит 12 тестовых классов, каждый из которых демонстрирует одну из возможностей Spring Core:

| Тест                             | Описание                 |
|----------------------------------|--------------------------|
| Section1_IoCContainerTest        | Работа с IoC контейнером |
| Section2_DependencyInjectionTest | Инъекция зависимостей    |
| Section3_ScopeTest               | Scope бинов              |
| Section4_LifecycleTest           | Жизненный цикл бина      |
| Section5_ConfigurationTest       | Конфигурация через @Bean |
| Section6_StereotypesTest         | Стереотипные аннотации   |
| Section7_AutowiredQualifierTest  | @Autowired и @Qualifier  |
| Section8_PropertyInjectionTest   | Инъекция свойств         |
| Section9_ContextAwareTest        | ApplicationContextAware  |
| Section10_ProfileTest            | Профили                  |
| Section11_EventTest              | Обработка событий        |
| Section12_ConditionalTest        | Условные бины            |

## Лицензия

Проект создан в образовательных целях.
