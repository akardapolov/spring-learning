# Spring Learning Project

Учебный проект, разбитый на модули для последовательного изучения экосистемы Spring и связанных с ней технологий.  

Каждый модуль представляет собой отдельный проект с собственной документацией и примерами кода.

## Содержание курса

### 1. Основы Web (Servlets & Tomcat)
Фундамент Java Web, на котором базируется Spring MVC.
* Жизненный цикл HTTP запроса и ответа
* Servlet API: init, service, destroy
* Фильтры (Filters) и слушатели (Listeners)
* Управление сессиями (Session management)
* Глоссарий терминов
* Структура проекта
* Endpoint'ы (URL маппинги)
* 🔗 [Документация модуля](./01-servlets/src/main/resources/static/index.html)

### 2. Spring Context (Core)
Базовые концепции Spring Framework без магии автоконфигураций.
* Inversion of Control (IoC) и Dependency Injection (DI)
* Жизненный цикл Bean'а и Scopes (Singleton, Prototype и др.)
* Способы конфигурации: XML, Java Config, Annotations
* ApplicationContext vs BeanFactory
* Основы AOP (Aspect-Oriented Programming) и проксирование
* Глоссарий терминов
* Структура проекта
* 🔗 [Документация модуля](./02-spring-context/src/main/resources/static/index.html)

### 3. Spring Boot
Магия автоконфигурации и быстрый старт приложений.
* Что такое Spring Boot и зачем он нужен
* Стартеры (Starters) и управление зависимостями
* Механизм Auto-configuration (@Conditional)
* Конфигурация через `application.yaml` / `application.properties`
* Spring Boot Actuator: метрики и мониторинг
* Глоссарий терминов
* Структура проекта
* Endpoint'ы (Actuator)
* 🔗 [Документация модуля](./03-spring-boot/src/main/resources/static/index.html)

### 4. Hibernate
Глубокое погружение в ORM.
* Что такое Hibernate и зачем он нужен
* Жизненный цикл сущности (Entity Lifecycle)
* Связи между сущностями (`OneToMany`, `ManyToOne` и др.)
* Стратегии загрузки: `LAZY` vs `EAGER`
* Проблема `N+1` и способы её решения (`Join Fetch`, `Entity Graph`)
* Кэширование (`L1`, `L2`, `Query Cache`)
* Advanced scenarios (`Batching`, `Optimistic Locking`)
* Глоссарий терминов
* Структура проекта
* Endpoint'ы API
* 🔗 [Документация модуля](./04-hibernate/src/main/resources/static/index.html)

### 5. Spring Data JPA
Абстракция над JPA и упрощение работы с базой данных.
* Spring Data Repository: иерархия интерфейсов
* Query Methods: генерация запросов по имени метода
* Использование `@Query` и нативных запросов
* Пагинация и сортировка (`Pageable`, `Slice`)
* Projections (проекции) для оптимизации выборок
* Specifications (спецификации) для динамической фильтрации
* Глоссарий терминов
* Структура проекта
* Endpoint'ы API
* 🔗 [Документация модуля](./05-spring-data-jpa/src/main/resources/static/index.html)

### 6. Spring Data JDBC
Легковесная альтернатива JPA без "магии" сессий.
* Отличия Spring Data JDBC от JPA
* Подход Domain-Driven Design (DDD) и агрегаты
* Настройка маппинга и связей
* Написание кастомных запросов
* События жизненного цикла (Lifecycle Events)
* Глоссарий терминов
* Структура проекта
* Endpoint'ы API
* 🔗 [Документация модуля](./06-spring-data-jdbc/src/main/resources/static/index.html)

### 7. Spring Web (REST API)
Создание современных и правильных RESTful веб-сервисов.
* Архитектура REST и уровни зрелости
* Контроллеры: `@RestController`, `@RequestMapping`
* Сериализация / десериализация (`Jackson JSON`)
* Глобальная обработка ошибок (`@ControllerAdvice`)
* Валидация входных данных (`Hibernate Validator`)
* Работа с `ResponseEntity` и HTTP-заголовками
* Глоссарий терминов
* Структура проекта
* Endpoint'ы API
* 🔗 [Документация модуля](./07-spring-rest/src/main/resources/static/index.html)

### 8. Spring Security
Обеспечение безопасности веб-приложений и API.
* Архитектура Security Filter Chain
* Аутентификация vs авторизация
* Управление пользователями (`UserDetailsService`)
* Хэширование паролей (`PasswordEncoder`)
* JWT (`JSON Web Tokens`) и Stateless security
* Method Security (`@PreAuthorize`, `@Secured`)
* Защита от атак: CORS и CSRF
* Глоссарий терминов
* Структура проекта
* Endpoint'ы API
* 🔗 [Документация модуля](./08-spring-security/src/main/resources/static/index.html)

## Структура проекта

Проект организован как **Maven multi-module project**.

```text
spring/
├─ pom.xml
├─ README.md
├─ .gitignore
├─ 01-servlets/
├─ 02-spring-context/
├─ 03-spring-boot/
├─ 04-hibernate/
├─ 05-spring-data-jpa/
├─ 06-spring-data-jdbc/
├─ 07-spring-rest/
└─ 08-spring-security/
```

## Как использовать

В каждой папке находится отдельный модуль.  
Для изучения конкретной темы:

1. Перейдите в папку нужного модуля.
2. Откройте `index.html` для изучения теории и устройства модуля.
3. При наличии исполняемого приложения запустите модуль и протестируйте доступные endpoint'ы.

## Сборка и запуск

### Сборка всего проекта

Из корневой папки проекта:

```bash
mvn clean install
```

Эта команда соберёт все модули.

### Сборка конкретного модуля

Пример для Servlets:

```bash
mvn -pl 01-servlets clean install
```

### Сборка и запуск конкретного модуля из корня проекта

#### 1. Основы Web (Servlets & Tomcat)
```bash
mvn -pl 01-servlets spring-boot:run
```

#### 2. Spring Context
```bash
mvn -pl 02-spring-context spring-boot:run
```

#### 3. Spring Boot
```bash
mvn -pl 03-spring-boot spring-boot:run
```

#### 4. Hibernate
```bash
mvn -pl 04-hibernate spring-boot:run
```

#### 5. Spring Data JPA
```bash
mvn -pl 05-spring-data-jpa spring-boot:run
```

#### 6. Spring Data JDBC
```bash
mvn -pl 06-spring-data-jdbc spring-boot:run
```

#### 7. Spring Web (REST API)
```bash
mvn -pl 07-spring-rest spring-boot:run
```

#### 8. Spring Security
```bash
mvn -pl 08-spring-security spring-boot:run
```

### Запуск из папки модуля

Можно перейти в директорию модуля и запускать его отдельно:

```bash
cd 04-hibernate
mvn spring-boot:run
```