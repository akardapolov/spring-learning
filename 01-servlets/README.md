# Servlets Demo

Демо-проект на Spring Boot + Servlet API.

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
java -jar target/01-servlets-1.0.0.jar
```

Приложение стартует на порту:
- `http://localhost:8885`

## Доступ к index.html

Файл `index.html` лежит в:
```
src/main/resources/static/index.html
```

После запуска приложения откройте в браузере:

- `http://localhost:8885/` — главная страница с документацией
- `http://localhost:8885/index.html` — прямая ссылка на документацию

Если нужно просто открыть страницу как статический файл, без API и без запуска Spring Boot:

1. Найдите файл `src/main/resources/static/index.html`.
2. Откройте его в браузере через IDE или напрямую из файловой системы.

Адрес будет вида:
```
file:///.../src/main/resources/static/index.html
```

## Что демонстрирует проект

Проект показывает основные возможности Servlet API:

### 1. Servlet API

- Базовый интерфейс `Servlet`
- `GenericServlet` — абстрактный класс
- `HttpServlet` — базовый класс для HTTP сервлетов
- Жизненный цикл сервлета: `init()`, `service()`, `destroy()`
- Аннотация `@WebServlet` для объявления сервлетов

### 2. Request API

- `HttpServletRequest` для доступа к информации о запросе
- Методы для получения параметров, заголовков, атрибутов
- Чтение body запроса
- Работа с query параметрами

### 3. Response API

- `HttpServletResponse` для формирования ответа
- Установка статус кодов
- Работа с заголовками
- Content Negotiation
- JSON ответы

### 4. Session/Cookie API

- `HttpSession` для работы с сессиями
- Создание и уничтожение сессий
- Атрибуты сессии
- Работа с куки
- Таймаут сессии

### 5. Filter API

- `Filter` интерфейс для перехвата запросов и ответов
- `FilterChain` для передачи управления следующему фильтру
- Жизненный цикл фильтра: `init()`, `doFilter()`, `destroy()`
- Аннотация `@WebFilter` для объявления фильтров

### 6. Listener API

- `ServletContextListener` — события жизненного цикла приложения
- `HttpSessionListener` — события создания/уничтожения сессий
- `ServletRequestListener` — события жизненного цикла запросов
- Аннотация `@WebListener` для объявления листенеров

### 7. Forward vs Redirect

- Forward — перенаправление на сервере
- Redirect — перенаправление на клиенте
- Различия в URL, атрибутах и производительности

### 8. Error Handling

- Установка статус кодов
- Отправка ошибок
- Обработка исключений

### 9. CRUD операции через сервлет

- Полный набор операций для ресурсов
- GET — чтение
- POST — создание
- PUT — обновление
- DELETE — удаление

## Основные endpoint'ы

### Demo endpoints (через Spring MVC контроллер)

- `GET /api/demo` — информация о приложении и доступных endpoint'ах
- `GET /api/demo/servlet-api` — обзор Servlet API
- `GET /api/demo/request-api` — Request API
- `GET /api/demo/response-api` — Response API
- `GET /api/demo/session-api` — Session/Cookie API
- `GET /api/demo/filter-api` — Filter API
- `GET /api/demo/listener-api` — Listener API
- `GET /api/demo/forward-redirect` — Forward vs Redirect
- `GET /api/demo/error-handling` — Error Handling

### Servlet endpoints

- `GET /api/basic/products` — список всех продуктов
- `GET /api/basic/products/{id}` — продукт по ID
- `POST /api/basic/products` — создание продукта
- `PUT /api/basic/products/{id}` — обновление продукта
- `DELETE /api/basic/products/{id}` — удаление продукта

- `GET /api/request` — информация о запросе
- `POST /api/request` — чтение body запроса

- `GET /api/response` — базовый ответ
- `GET /api/response/status/{code}` — тест статус кодов
- `GET /api/response/redirect?to=/path` — редирект

- `GET /api/session` — информация о сессии и куках
- `POST /api/session` — установить атрибут сессии
- `DELETE /api/session?key={name}` — удалить атрибут сессии

- `GET /api/forward/to-welcome` — forward к welcome
- `GET /api/redirect/temporary` — временный редирект (302)
- `GET /api/redirect/permanent` — постоянный редирект (301)

- `GET /api/error/bad-request` — 400 Bad Request
- `GET /api/error/not-found` — 404 Not Found
- `GET /api/error/server-error` — 500 Internal Server Error

## Структура проекта

```
src
├── main
│   ├── java/com/example/servlets
│   │   ├── ServletsApplication.java
│   │   ├── config
│   │   │   └── DemoDataInitializer.java
│   │   ├── controller
│   │   │   └── DemoController.java
│   │   ├── filter
│   │   │   ├── LoggingFilter.java
│   │   │   └── TimingFilter.java
│   │   ├── listener
│   │   │   ├── ApplicationServletContextListener.java
│   │   │   ├── ApplicationHttpSessionListener.java
│   │   │   └── ApplicationServletRequestListener.java
│   │   ├── model
│   │   │   ├── Product.java
│   │   │   ├── Category.java
│   │   │   ├── Tag.java
│   │   │   └── ApiModels.java
│   │   └── servlet
│   │       ├── BasicHttpServlet.java
│   │       ├── RequestApiServlet.java
│   │       ├── ResponseApiServlet.java
│   │       ├── SessionCookieServlet.java
│   │       ├── ForwardRedirectServlet.java
│   │       └── ErrorHandlingServlet.java
│   └── resources
│       ├── static
│       │   └── index.html
│       └── application.yaml
└── test
    └── java/com/example/servlets
        ├── Section1_BasicHttpServletTest.java
        ├── Section2_RequestApiTest.java
        ├── Section3_ResponseApiTest.java
        ├── Section4_SessionCookieTest.java
        ├── Section5_ForwardRedirectTest.java
        ├── Section6_ErrorHandlingTest.java
        ├── Section7_DemoControllerTest.java
        ├── Section8_FiltersTest.java
        └── Section9_ListenersTest.java
```

## Тесты

Запуск всех тестов:

```bash
mvn test
```

Проект содержит интеграционные тесты для:
- Базового HttpServlet и CRUD операций
- Request API
- Response API
- Session/Cookie API
- Forward vs Redirect
- Error Handling
- Demo Controller
- Filters
- Listeners

Каждый тест находится в отдельном файле и охватывает конкретную функциональность Servlet API.
