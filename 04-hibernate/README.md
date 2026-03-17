# Hibernate Demo

Демо‑проект на Spring Boot + Hibernate + H2.

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
java -jar target/hibernate-demo-1.0.0.jar
```

Приложение стартует на порту, указанном в `application.yaml`:

- `http://localhost:8888`

## Доступ к index.html

Файл `index.html` лежит в:

```text
src/main/resources/static/index.html
```

После запуска приложения откройте в браузере:

- `http://localhost:8888/`  
  или
- `http://localhost:8888/index.html`

## Просмотр index.html без бэкенда (только вёрстка)

Если нужно просто открыть страницу как статический файл (без работы API и без Thymeleaf):

1. Найдите файл `src/main/resources/static/index.html`.
2. Откройте его в браузере (через IDE: *Open in Browser* либо двойной клик по файлу в файловой системе).

Адрес будет вида:

```text
file:///.../src/main/resources/static/index.html
```

## Консоль H2

Консоль встроенной БД доступна по адресу:

- `http://localhost:8888/h2-console`

Настройки подключения:

- JDBC URL: `jdbc:h2:mem:demodb`
- User Name: `sa`
- Password: пусто
