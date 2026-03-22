# Spring Security Demo

Демо‑проект на Spring Boot + Spring Security + H2.

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
java -jar target/08-spring-security-1.0.0.jar
```

Приложение стартует на порту, указанном в `application.yaml`:

- `http://localhost:8892`

## Доступ к index.html

Файл `index.html` лежит в:

```text
src/main/resources/static/index.html
```

После запуска приложения откройте в браузере:

- `http://localhost:8892/`
  или
- `http://localhost:8892/index.html`

## Консоль H2

Консоль встроенной БД доступна по адресу:

- `http://localhost:8892/h2-console`

Настройки подключения:

- JDBC URL: `jdbc:h2:mem:springsecurity`
- User Name: `sa`
- Password: пусто

## Что демонстрирует проект

Проект показывает основные возможности Spring Security:

- базовые концепции безопасности (Authentication vs Authorization)
- цепочку фильтров Spring Security (Security Filter Chain)
- настройку безопасности через SecurityConfiguration
- Method Security аннотации (@PreAuthorize, @Secured)
- шифрование паролей (BCryptPasswordEncoder)
- управление пользователями, ролями и разрешениями
- сессионное управление
- архитектуру Spring Security
- работу с in-memory базой H2 через JPA

## Основные endpoint'ы

### Публичные (без авторизации)

- `GET /api/auth/login` - авторизация пользователя
- `GET /api/security/concepts` - концепции безопасности
- `GET /api/security/filter-chain` - цепочка фильтров
- `GET /api/security/architecture` - архитектура Spring Security
- `GET /api/security/auth-vs-authorization` - отличие Auth от Authorization
- `GET /api/security/method-security` - Method Security
- `GET /api/security/password-encoder` - шифрование паролей
- `GET /api/security/config` - конфигурация безопасности
- `GET /api/security/user-details/{username}` - детали пользователя
- `GET /api/security/events` - события безопасности
- `GET /api/security/glossary` - глоссарий
- `GET /api/security/endpoints` - список endpoint'ов

### Требующие авторизации

- `GET /api/users` - список пользователей
- `GET /api/users/{id}` - получить пользователя по ID
- `POST /api/users` - создать пользователя
- `PUT /api/users/{id}` - обновить пользователя
- `DELETE /api/users/{id}` - удалить пользователя
- `PUT /api/users/{id}/password` - изменить пароль
- `GET /api/roles` - список ролей
- `GET /api/roles/{id}` - получить роль по ID
- `POST /api/roles` - создать роль
- `PUT /api/roles/{id}` - обновить роль
- `DELETE /api/roles/{id}` - удалить роль
- `GET /api/permissions` - список разрешений
- `GET /api/permissions/{id}` - получить разрешение по ID
- `POST /api/permissions` - создать разрешение

## Структура проекта

```text
src
├── main
│   ├── java/com/example/springsecurity
│   │   ├── config
│   │   │   ├── DemoDataInitializer.java
│   │   │   └── SecurityConfiguration.java
│   │   ├── controller
│   │   │   ├── AuthController.java
│   │   │   ├── DemoController.java
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── PermissionController.java
│   │   │   ├── RoleController.java
│   │   │   └── UserController.java
│   │   ├── entity
│   │   │   ├── Permission.java
│   │   │   ├── Role.java
│   │   │   └── User.java
│   │   ├── exception
│   │   │   ├── RoleNotFoundException.java
│   │   │   ├── UserAlreadyExistsException.java
│   │   │   └── UserNotFoundException.java
│   │   ├── model
│   │   │   └── ApiModels.java
│   │   ├── repository
│   │   │   ├── PermissionRepository.java
│   │   │   ├── RoleRepository.java
│   │   │   └── UserRepository.java
│   │   └── service
│   │       ├── PermissionService.java
│   │       ├── RoleService.java
│   │       ├── SpringSecurityDemoService.java
│   │       └── UserService.java
│   └── resources
│       ├── static
│       │   └── index.html
│       └── application.yaml
└── test
    └── java/com/example/springsecurity
        ├── Section1_SecurityConceptsTest.java
        ├── Section2_FilterChainTest.java
        ├── Section3_AuthVsAuthorizationTest.java
        ├── Section4_MethodSecurityTest.java
        ├── Section5_SecurityConfigTest.java
        ├── Section6_GlossaryTest.java
        ├── Section7_EndpointsTest.java
        └── Section8_ArchitectureTest.java
```

## Тесты

Запуск всех тестов:

```bash
mvn test
```

Проект содержит интеграционные тесты для:

- базовых концепций безопасности
- цепочки фильтров Spring Security
- отличия Authentication от Authorization
- Method Security аннотаций
- конфигурации безопасности
- глоссария терминов
- REST endpoint'ов через MockMvc
- архитектуры Spring Security
