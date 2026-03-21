# Spring REST Demo

Демо-проект на Spring Boot + Spring Web MVC.

## Требования

- JDK 21+
- Maven 3.9+

## Запуск приложения локально

Из корня проекта:

### Вариант 1. Через Maven

```bash
mvn clean spring-boot:run
```

### Вариант 2. Через собранный JAR

```bash
mvn clean package
java -jar target/07-spring-rest-1.0.0.jar
```

Приложение стартует на порту, указанном в `application.yaml`:
- `http://localhost:8891`

## Доступ к index.html

Файл `index.html` лежит в:
```
src/main/resources/static/index.html
```

После запуска приложения откройте в браузере:

- `http://localhost:8891/`
- или
- `http://localhost:8891/index.html`

Если нужно просто открыть страницу как статический файл, без API и без запуска Spring Boot:

1. Найдите файл `src/main/resources/static/index.html`.
2. Откройте его в браузере через IDE или напрямую из файловой системы.

Адрес будет вида:
```
file:///.../src/main/resources/static/index.html
```

## Консоль H2

Консоль встроенной БД доступна по адресу:
- `http://localhost:8891/h2-console`

Настройки подключения:

- JDBC URL: `jdbc:h2:mem:springrest`
- User Name: `sa`
- Password: пустое (оставьте поле пустым)

## Что демонстрирует проект

Проект показывает основные возможности Spring Web MVC для создания REST API:

### 1. @RestController и HTTP маппинг

- `@RestController` вместо `@Controller` + `@ResponseBody`
- `@RequestMapping` и его вариации (`@GetMapping`, `@PostMapping`, `@PutMapping`, `@PatchMapping`, `@DeleteMapping`)
- Различия между HTTP методами (GET, POST, PUT, PATCH, DELETE)

### 2. Параметры запроса

- `@PathVariable` - переменные пути (например, `/products/{id}`)
- `@RequestParam` - query параметры (например, `?page=0&size=10`)
- `@RequestBody` - тело запроса (JSON для POST/PUT/PATCH)
- `@RequestHeader` - заголовки
- `@CookieValue` - куки

### 3. Обработка ответов

- `ResponseEntity` для управления статусом, заголовками и телом ответа
- `@ResponseStatus` для указания HTTP статуса
- Коды статусов (200, 201, 204, 400, 404, 500)
- Content Negotiation

### 4. Обработка исключений

- `@ControllerAdvice` для глобальной обработки ошибок
- `@ExceptionHandler` для обработки конкретных исключений
- Структурированные ответы об ошибках с полями: status, error, message, timestamp, path, validationErrors

### 5. Валидация

- `@Valid` для активации валидации
- Bean Validation аннотации (`@NotNull`, `@NotBlank`, `@Size`, `@DecimalMin`, `@Email` и т.д.)
- Обработка ошибок валидации с детальными сообщениями о поле и значении

### 6. DTO vs Entity

- Разделение DTO (Data Transfer Object) и Entity
- `ProductDto`, `ProductCreateRequest`, `ProductUpdateRequest` для работы с API
- Отдельные DTO для создания и обновления
- Маппинг Entity → DTO в сервисном слое

### 7. CRUD операции

- Полный набор операций для ресурсов
- `GET` - чтение
- `POST` - создание (возвращает 201 Created)
- `PUT` - полное обновление
- `PATCH` - частичное обновление
- `DELETE` - удаление (возвращает 204 No Content)

### 8. Пагинация и сортировка

- `Pageable` для пагинации и сортировки
- `Page` с информацией о количестве элементов и страницах
- Параметры `page`, `size`, `sortBy`, `sortDir`

### 9. REST API Best Practices

- Правильные HTTP методы для операций
- Структурированные ответы об ошибках
- Content Negotiation
- RESTful URI дизайн

## Основные endpoint'ы

### Demo endpoints

- `GET /api/rest/basics` - демонстрация @RestController и аннотаций
- `GET /api/rest/http-methods` - демонстрация HTTP методов
- `POST /api/rest/http-methods`
- `PUT /api/rest/http-methods`
- `GET /api/rest/http-methods/head`
- `GET /api/rest/parameters/path/{id}` - переменные пути
- `POST /api/rest/parameters/body` - тело запроса
- `GET /api/rest/response/{type}` - обработка ответов
- `GET /api/rest/response/custom-headers` - кастомные заголовки
- `GET /api/rest/exception/{type}` - обработка исключений
- `POST /api/rest/validation` - валидация

### Product endpoints

- `GET /api/rest/products` - список всех продуктов
- `GET /api/rest/products/{id}` - продукт по ID
- `POST /api/rest/products` - создание продукта
- `PUT /api/rest/products/{id}` - полное обновление
- `PATCH /api/rest/products/{id}` - частичное обновление
- `DELETE /api/rest/products/{id}` - удаление продукта
- `GET /api/rest/products/paginated` - пагинация и сортировка
- `GET /api/rest/products/search` - поиск с фильтрами

### Category endpoints

- `GET /api/rest/categories` - список всех категорий
- `GET /api/rest/categories/{id}` - категория по ID
- `GET /api/rest/categories/{id}/products` - категория с продуктами
- `POST /api/rest/categories` - создание категории

## Структура проекта

```
src
├── main
│   ├── java/com/example/springrest
│   │   ├── SpringRestApplication.java
│   │   ├── config
│   │   │   └── DemoDataInitializer.java
│   │   ├── controller
│   │   │   ├── DemoController.java
│   │   │   ├── ProductController.java
│   │   │   ├── CategoryController.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── dto
│   │   │   ├── ProductDto.java
│   │   │   ├── ProductCreateRequest.java
│   │   │   ├── ProductUpdateRequest.java
│   │   │   ├── CategoryDto.java
│   │   │   ├── ErrorResponse.java
│   │   │   └── ValidationError.java
│   │   ├── entity
│   │   │   ├── Product.java
│   │   │   ├── Category.java
│   │   │   ├── ProductDetails.java
│   │   │   ├── Tag.java
│   │   │   ├── Customer.java
│   │   │   └── CustomerOrder.java
│   │   ├── exception
│   │   │   ├── ProductNotFoundException.java
│   │   │   └── CategoryNotFoundException.java
│   │   ├── model
│   │   │   └── ApiModels.java
│   │   ├── repository
│   │   │   ├── ProductRepository.java
│   │   │   └── CategoryRepository.java
│   │   └── service
│   │       └── RestDemoService.java
│   └── resources
│       ├── static
│       │   └── index.html
│       └── application.yaml
└── test
    └── java/com/example/springrest
        ├── Section1_ControllerBasicsTest.java
        ├── Section2_RequestParametersTest.java
        ├── Section3_ResponseHandlingTest.java
        ├── Section4_ExceptionHandlingTest.java
        ├── Section5_ValidationTest.java
        ├── Section6_CrudOperationsTest.java
        ├── Section7_PaginationAndSortingTest.java
        └── Section8_HttpContractTest.java
```

## Тесты

Запуск всех тестов:

```bash
mvn test
```

Проект содержит интеграционные тесты для:
- Основ контроллеров и HTTP маппинга
- Обработки параметров запроса
- Обработки ответов
- Обработки исключений
- Валидации
- CRUD операций
- Пагинации и сортировки
- HTTP контрактов (MockMvc)

Каждый тест находится в отдельном файле и охватывает конкретную функциональность Spring REST.
