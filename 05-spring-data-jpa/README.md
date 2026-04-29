# Spring Data JPA Demo

Демо‑проект на Spring Boot + Spring Data JPA + H2.

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
java -jar target/05-spring-data-jpa-1.0.0.jar
```

Приложение стартует на порту, указанном в `application.yaml`:

- `http://localhost:8889`

## Доступ к index.html

Файл `index.html` лежит в:

```text
src/main/resources/static/index.html
```

После запуска приложения откройте в браузере:

- `http://localhost:8889/`
  или
- `http://localhost:8889/index.html`

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

- `http://localhost:8889/h2-console`

Настройки подключения:

- JDBC URL: `jdbc:h2:mem:springdatajpa`
- User Name: `sa`
- Password: пусто

## Что демонстрирует проект

Проект показывает основные возможности Spring Data JPA:

- Spring Data Repository и иерархию интерфейсов
- Query Methods
- `@Query` и native query
- пагинацию и сортировку
- `Pageable`, `Page`, `Slice`
- projections
- specifications
- `@EntityGraph` в репозиториях
- работу с JPA‑моделями, сохранёнными максимально близко к модулю Hibernate

## Основные endpoint'ы

- `GET /api/demo/repository-hierarchy`
- `GET /api/demo/query-methods`
- `GET /api/demo/query-annotation`
- `GET /api/demo/pagination/page?page=0&size=3`
- `GET /api/demo/pagination/slice?page=0&size=2`
- `GET /api/demo/projections`
- `GET /api/demo/specifications?type=DEVICE&name=pho&minPrice=500`

## Структура проекта

```text
src
├── main
│   ├── java/com/example/springdatajpa
│   │   ├── config
│   │   ├── controller
│   │   ├── entity
│   │   ├── exception
│   │   ├── model
│   │   ├── repository
│   │   │   ├── projection
│   │   │   └── specification
│   │   └── service
│   └── resources
│       ├── static
│       └── application.yaml
└── test
    └── java/com/example/springdatajpa
```

## Тесты

Запуск всех тестов:

```bash
mvn test
```

Проект содержит интеграционные тесты для:

- иерархии репозиториев
- query methods
- `@Query` и native query
- pagination и slice
- projections
- specifications
- `@EntityGraph`