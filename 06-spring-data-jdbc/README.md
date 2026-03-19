# Spring Data JDBC Demo

Демо‑проект на Spring Boot + Spring Data JDBC + H2.

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
mvn clean spring-boot:run
```

### Вариант 2. Через собранный JAR

```bash
mvn clean package
java -jar target/06-spring-data-jdbc-1.0.0.jar
```

Приложение стартует на порту, указанном в `application.yaml`:

- `http://localhost:8890`

## Доступ к index.html

Файл `index.html` лежит в:

```text
src/main/resources/static/index.html
```

После запуска приложения откройте в браузере:

- `http://localhost:8890/`
  или
- `http://localhost:8890/index.html`

## Просмотр index.html без бэкенда (только вёрстка)

Если нужно просто открыть страницу как статический файл, без API и без запуска Spring Boot:

1. Найдите файл `src/main/resources/static/index.html`.
2. Откройте его в браузере через IDE или напрямую из файловой системы.

Адрес будет вида:

```text
file:///.../src/main/resources/static/index.html
```

## Консоль H2

Консоль встроенной БД доступна по адресу:

- `http://localhost:8890/h2-console`

Настройки подключения:

- JDBC URL: `jdbc:h2:mem:springdatajdbc`
- User Name: `sa`
- Password: пусто

## Что демонстрирует проект

Проект показывает основные возможности Spring Data JDBC:

- отличия Spring Data JDBC от JPA
- агрегаты и подход DDD
- `@MappedCollection`
- `@Embedded`
- `AggregateReference`
- custom queries
- lifecycle events
- CRUD API для агрегатов
- работу с in-memory базой H2 через `schema.sql`

## Основные endpoint'ы

- `GET /api/jdbc/differences`
- `GET /api/jdbc/ddd`
- `GET /api/jdbc/categories`
- `GET /api/jdbc/categories/{id}`
- `POST /api/jdbc/categories`
- `PUT /api/jdbc/categories/{id}`
- `DELETE /api/jdbc/categories/{id}`
- `POST /api/jdbc/categories/{id}/products`
- `PUT /api/jdbc/categories/{catId}/products/{prodId}`
- `DELETE /api/jdbc/categories/{catId}/products/{prodId}`
- `GET /api/jdbc/customers/{id}`
- `POST /api/jdbc/customers`
- `POST /api/jdbc/customers/{id}/orders`
- `GET /api/jdbc/custom-query?title=...`
- `GET /api/jdbc/custom-query/min-price?minPrice=...`
- `GET /api/jdbc/lifecycle`
- `GET /api/jdbc/glossary`
- `GET /api/jdbc/endpoints`

## Структура проекта

```text
src
├── main
│   ├── java/com/example/springdatajdbc
│   │   ├── config
│   │   ├── controller
│   │   ├── entity
│   │   ├── model
│   │   ├── repository
│   │   └── service
│   └── resources
│       ├── static
│       ├── application.yaml
│       └── schema.sql
└── test
    └── java/com/example/springdatajdbc
```

## Тесты

Запуск всех тестов:

```bash
mvn test
```

Проект содержит интеграционные тесты для:

- различий Spring Data JDBC и JPA
- агрегатов DDD
- mapping и связей
- custom queries
- lifecycle events
- glossary
- CRUD API
- REST endpoint'ов через MockMvc