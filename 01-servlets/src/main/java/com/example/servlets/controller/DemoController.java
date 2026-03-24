package com.example.servlets.controller;

import com.example.servlets.listener.ApplicationHttpSessionListener;
import com.example.servlets.model.ApiModels;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * REST контроллер для демонстрации Servlet API через Spring MVC.
 * Объединяет все демо-методы в одном месте.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

  @GetMapping
  public ApiModels.HttpResponse index() {
    Map<String, Object> data = new LinkedHashMap<>();

    Map<String, Object> appInfo = new LinkedHashMap<>();
    appInfo.put("application", Map.of(
        "name", "Servlets Demo Application",
        "version", "1.0.0",
        "port", "8885"
    ));
    data.put("application", appInfo);

    Map<String, Object> endpointsInfo = new LinkedHashMap<>();
    endpointsInfo.put("/api/demo", "This information");
    endpointsInfo.put("/api/demo/servlet-api", "Servlet API overview");
    endpointsInfo.put("/api/demo/request-api", "Request API demo");
    endpointsInfo.put("/api/demo/response-api", "Response API demo");
    endpointsInfo.put("/api/demo/session-api", "Session/Cookie API demo");
    endpointsInfo.put("/api/demo/filter-api", "Filter API demo");
    endpointsInfo.put("/api/demo/listener-api", "Listener API demo");
    endpointsInfo.put("/api/demo/forward-redirect", "Forward vs Redirect demo");
    endpointsInfo.put("/api/demo/error-handling", "Error handling demo");
    data.put("endpoints", endpointsInfo);

    return new ApiModels.HttpResponse(200, "Servlets Demo", data);
  }

  @GetMapping("/servlet-api")
  public ApiModels.HttpResponse servletApi() {
    Map<String, Object> data = new LinkedHashMap<>();

    Map<String, Object> interfacesInfo = new LinkedHashMap<>();
    interfacesInfo.put("Servlet", "Базовый интерфейс для всех сервлетов");
    interfacesInfo.put("GenericServlet", "Абстрактный класс, реализующий Servlet");
    interfacesInfo.put("HttpServlet", "Базовый класс для HTTP сервлетов");
    data.put("interfaces", interfacesInfo);

    Map<String, Object> lifecycleInfo = new LinkedHashMap<>();
    lifecycleInfo.put("init()", "Инициализация сервлета");
    lifecycleInfo.put("service()", "Обработка запросов");
    lifecycleInfo.put("destroy()", "Уничтожение сервлета");
    data.put("lifecycle", lifecycleInfo);

    Map<String, Object> methodsInfo = new LinkedHashMap<>();
    methodsInfo.put("doGet()", "Обработка GET запросов");
    methodsInfo.put("doPost()", "Обработка POST запросов");
    methodsInfo.put("doPut()", "Обработка PUT запросов");
    methodsInfo.put("doDelete()", "Обработка DELETE запросов");
    methodsInfo.put("doOptions()", "Обработка OPTIONS запросов");
    methodsInfo.put("doHead()", "Обработка HEAD запросов");
    data.put("methods", methodsInfo);

    Map<String, Object> annotationsInfo = new LinkedHashMap<>();
    annotationsInfo.put("@WebServlet", "Объявление сервлета");
    annotationsInfo.put("@WebFilter", "Объявление фильтра");
    annotationsInfo.put("@WebListener", "Объявление листенера");
    annotationsInfo.put("@WebInitParam", "Параметры инициализации");
    data.put("annotations", annotationsInfo);

    return new ApiModels.HttpResponse(200, "Servlet API Overview", data);
  }

  @GetMapping("/request-api")
  public ApiModels.HttpResponse requestApi(@RequestParam(defaultValue = "true") boolean full) {
    Map<String, Object> data = new LinkedHashMap<>();

    if (full) {
      data.put("description", "HttpServletRequest предоставляет доступ к информации о запросе");
      Map<String, Object> methodsInfo = new LinkedHashMap<>();
      methodsInfo.put("getMethod()", "HTTP метод (GET, POST, ...)");
      methodsInfo.put("getRequestURI()", "URI запроса");
      methodsInfo.put("getContextPath()", "Контекст приложения");
      methodsInfo.put("getServletPath()", "Путь к сервлету");
      methodsInfo.put("getQueryString()", "Query string");
      methodsInfo.put("getParameter(name)", "Значение параметра");
      methodsInfo.put("getParameterMap()", "Все параметры");
      methodsInfo.put("getHeader(name)", "Значение заголовка");
      methodsInfo.put("getHeaders(name)", "Все значения заголовка");
      methodsInfo.put("getRemoteAddr()", "IP адрес клиента");
      methodsInfo.put("getCookies()", "Массив куки");
      methodsInfo.put("getSession()", "HTTP сессия");
      methodsInfo.put("getAttribute(name)", "Атрибут запроса");
      methodsInfo.put("setAttribute(name, value)", "Установка атрибута");
      data.put("methods", methodsInfo);
    }

    Map<String, Object> endpointsInfo = new LinkedHashMap<>();
    endpointsInfo.put("GET /api/request", "Информация о запросе");
    endpointsInfo.put("GET /api/request/params", "Параметры запроса");
    endpointsInfo.put("GET /api/request/headers", "Заголовки запроса");
    endpointsInfo.put("GET /api/request/attributes", "Атрибуты запроса");
    endpointsInfo.put("POST /api/request", "Чтение body запроса");
    data.put("endpoints", endpointsInfo);

    return new ApiModels.HttpResponse(200, "Request API", data);
  }

  @GetMapping("/response-api")
  public ApiModels.HttpResponse responseApi() {
    Map<String, Object> data = new LinkedHashMap<>();

    data.put("description", "HttpServletResponse позволяет формировать ответ клиенту");

    Map<String, Object> methodsInfo = new LinkedHashMap<>();
    methodsInfo.put("setStatus(code)", "Установить статус код");
    methodsInfo.put("setStatus(code, message)", "Статус с сообщением");
    methodsInfo.put("sendError(code)", "Отправить ошибку");
    methodsInfo.put("sendRedirect(location)", "Перенаправление");
    methodsInfo.put("setHeader(name, value)", "Установить заголовок");
    methodsInfo.put("setContentType(type)", "Тип контента");
    methodsInfo.put("setCharacterEncoding(enc)", "Кодировка");
    methodsInfo.put("getWriter()", "Писатель для текста");
    methodsInfo.put("getOutputStream()", "Поток для бинарных данных");
    data.put("methods", methodsInfo);

    Map<String, Object> endpointsInfo = new LinkedHashMap<>();
    endpointsInfo.put("GET /api/response", "Базовый ответ");
    endpointsInfo.put("GET /api/response/status/{code}", "Тест статус кодов");
    endpointsInfo.put("GET /api/response/headers", "Заголовки ответа");
    endpointsInfo.put("GET /api/response/content?type=html|text|xml", "Типы контента");
    endpointsInfo.put("GET /api/response/redirect?to=/path", "Редирект");
    endpointsInfo.put("GET /api/response/encoding", "Кодировка");
    endpointsInfo.put("GET /api/response/buffer", "Работа с буфером");
    data.put("endpoints", endpointsInfo);

    Map<String, Object> statusCodesInfo = new LinkedHashMap<>();
    statusCodesInfo.put("200", "OK");
    statusCodesInfo.put("201", "Created");
    statusCodesInfo.put("204", "No Content");
    statusCodesInfo.put("301", "Moved Permanently");
    statusCodesInfo.put("302", "Found");
    statusCodesInfo.put("400", "Bad Request");
    statusCodesInfo.put("401", "Unauthorized");
    statusCodesInfo.put("403", "Forbidden");
    statusCodesInfo.put("404", "Not Found");
    statusCodesInfo.put("405", "Method Not Allowed");
    statusCodesInfo.put("500", "Internal Server Error");
    statusCodesInfo.put("503", "Service Unavailable");
    data.put("statusCodes", statusCodesInfo);

    return new ApiModels.HttpResponse(200, "Response API", data);
  }

  @GetMapping("/session-api")
  public ApiModels.HttpResponse sessionApi() {
    Map<String, Object> data = new LinkedHashMap<>();

    data.put("description", "HTTP сессия позволяет сохранять состояние между запросами");

    Map<String, Object> sessionMethodsInfo = new LinkedHashMap<>();
    sessionMethodsInfo.put("getSession()", "Получить или создать сессию");
    sessionMethodsInfo.put("getSession(false)", "Получить существующую сессию");
    sessionMethodsInfo.put("getId()", "ID сессии");
    sessionMethodsInfo.put("isNew()", "Сессия новая?");
    sessionMethodsInfo.put("getAttribute(name)", "Получить атрибут");
    sessionMethodsInfo.put("setAttribute(name, value)", "Установить атрибут");
    sessionMethodsInfo.put("removeAttribute(name)", "Удалить атрибут");
    sessionMethodsInfo.put("invalidate()", "Уничтожить сессию");
    sessionMethodsInfo.put("getMaxInactiveInterval()", "Таймаут (секунды)");
    sessionMethodsInfo.put("setMaxInactiveInterval()", "Установить таймаут");
    data.put("sessionMethods", sessionMethodsInfo);

    Map<String, Object> cookieMethodsInfo = new LinkedHashMap<>();
    cookieMethodsInfo.put("new Cookie(name, value)", "Создать куку");
    cookieMethodsInfo.put("response.addCookie(cookie)", "Добавить куку");
    cookieMethodsInfo.put("request.getCookies()", "Получить все куки");
    cookieMethodsInfo.put("cookie.getName()", "Имя куки");
    cookieMethodsInfo.put("cookie.getValue()", "Значение куки");
    cookieMethodsInfo.put("cookie.setMaxAge(age)", "Время жизни (секунды)");
    cookieMethodsInfo.put("cookie.setPath(path)", "Путь для куки");
    cookieMethodsInfo.put("cookie.setDomain(domain)", "Домен куки");
    cookieMethodsInfo.put("cookie.setSecure(true)", "Только HTTPS");
    cookieMethodsInfo.put("cookie.setHttpOnly(true)", "Доступ только серверу");
    data.put("cookieMethods", cookieMethodsInfo);

    Map<String, Object> endpointsInfo = new LinkedHashMap<>();
    endpointsInfo.put("GET /api/session", "Информация о сессии и куках");
    endpointsInfo.put("GET /api/session/create", "Создать сессию и куки");
    endpointsInfo.put("GET /api/session/invalidate", "Удалить сессию и куки");
    endpointsInfo.put("GET /api/session/attributes", "Атрибуты сессии");
    endpointsInfo.put("GET /api/session/cookies", "Информация о куках");
    endpointsInfo.put("POST /api/session", "Установить атрибут сессии (key, value в body)");
    endpointsInfo.put("DELETE /api/session?key={name}", "Удалить атрибут сессии");
    data.put("endpoints", endpointsInfo);

    return new ApiModels.HttpResponse(200, "Session/Cookie API", data);
  }

  @GetMapping("/filter-api")
  public ApiModels.HttpResponse filterApi() {
    Map<String, Object> data = new LinkedHashMap<>();

    data.put("description", "Фильтры позволяют перехватывать запросы и ответы");

    Map<String, Object> lifecycleInfo = new LinkedHashMap<>();
    lifecycleInfo.put("init()", "Инициализация фильтра");
    lifecycleInfo.put("doFilter()", "Обработка запроса/ответа");
    lifecycleInfo.put("destroy()", "Уничтожение фильтра");
    data.put("lifecycle", lifecycleInfo);

    Map<String, Object> methodsInfo = new LinkedHashMap<>();
    methodsInfo.put("doFilter(req, resp, chain)", "Обработка и передача следующему фильтру");
    methodsInfo.put("chain.doFilter()", "Передача управления следующему фильтру/сервлету");
    data.put("methods", methodsInfo);

    Map<String, Object> configurationInfo = new LinkedHashMap<>();
    configurationInfo.put("@WebFilter(urlPatterns)", "URL паттерны");
    configurationInfo.put("@WebFilter(servletNames)", "Имена сервлетов");
    configurationInfo.put("@WebFilter(dispatcherTypes)", "Типы диспатчера (REQUEST, ASYNC, ...)");
    configurationInfo.put("@WebInitParam", "Параметры инициализации");
    configurationInfo.put("urlPatterns", "/*, /api/*, /users/* и т.д.");
    configurationInfo.put("filterName", "Имя фильтра");
    data.put("configuration", configurationInfo);

    Map<String, Object> useCasesInfo = new LinkedHashMap<>();
    useCasesInfo.put("Логирование", "LoggingFilter - логирует все запросы");
    useCasesInfo.put("Аутентификация", "Проверка токена/сессии");
    useCasesInfo.put("Авторизация", "Проверка прав доступа");
    useCasesInfo.put("CORS", "Добавление CORS заголовков");
    useCasesInfo.put("Сжатие", "GZIP сжатие ответа");
    useCasesInfo.put("Кэширование", "Проверка кэша (ETag, Last-Modified)");
    useCasesInfo.put("Мониторинг", "Измерение времени выполнения");
    useCasesInfo.put("Encoding", "Установка кодировки");
    data.put("useCases", useCasesInfo);

    Map<String, Object> activeFiltersInfo = new LinkedHashMap<>();
    activeFiltersInfo.put("loggingFilter", "Логирует все запросы (/*)");
    activeFiltersInfo.put("timingFilter", "Измеряет время выполнения (/api/*)");
    data.put("activeFilters", activeFiltersInfo);

    return new ApiModels.HttpResponse(200, "Filter API", data);
  }

  @GetMapping("/listener-api")
  public ApiModels.HttpResponse listenerApi() {
    Map<String, Object> data = new LinkedHashMap<>();

    data.put("description", "Листенеры реагируют на события жизненного цикла");

    Map<String, Object> servletContextListenerInfo = new LinkedHashMap<>();
    servletContextListenerInfo.put("contextInitialized()", "При инициализации приложения");
    servletContextListenerInfo.put("contextDestroyed()", "При уничтожении приложения");
    servletContextListenerInfo.put("useCases", "Инициализация ресурсов, чтение конфигурации");
    data.put("servletContextListener", servletContextListenerInfo);

    Map<String, Object> httpSessionListenerInfo = new LinkedHashMap<>();
    httpSessionListenerInfo.put("sessionCreated()", "При создании сессии");
    httpSessionListenerInfo.put("sessionDestroyed()", "При уничтожении сессии");
    httpSessionListenerInfo.put("useCases", "Подсчет активных сессий, сохранение статистики");
    data.put("httpSessionListener", httpSessionListenerInfo);

    Map<String, Object> servletRequestListenerInfo = new LinkedHashMap<>();
    servletRequestListenerInfo.put("requestInitialized()", "При получении запроса");
    servletRequestListenerInfo.put("requestDestroyed()", "После обработки запроса");
    servletRequestListenerInfo.put("useCases", "Логирование запросов, измерение времени");
    data.put("servletRequestListener", servletRequestListenerInfo);

    Map<String, Object> activeListenersInfo = new LinkedHashMap<>();
    activeListenersInfo.put("ApplicationServletContextListener", "Логирует события контекста");
    activeListenersInfo.put("ApplicationHttpSessionListener", "Подсчитывает активные сессии");
    activeListenersInfo.put("ApplicationServletRequestListener", "Логирует жизненный цикл запросов");
    data.put("activeListeners", activeListenersInfo);

    Map<String, Object> listenersCountInfo = new LinkedHashMap<>();
    listenersCountInfo.put("activeSessions", ApplicationHttpSessionListener.getActiveSessionCount());
    data.put("listenersCount", listenersCountInfo);

    return new ApiModels.HttpResponse(200, "Listener API", data);
  }

  @GetMapping("/forward-redirect")
  public ApiModels.HttpResponse forwardRedirect() {
    Map<String, Object> data = new LinkedHashMap<>();

    Map<String, Object> forwardInfo = new LinkedHashMap<>();
    forwardInfo.put("description", "Перенаправление происходит на сервере, клиент не знает об этом");
    forwardInfo.put("url", "Не меняется в браузере");
    forwardInfo.put("attributes", "Атрибуты запроса сохраняются");
    forwardInfo.put("performance", "Быстрее, т.к. нет round-trip");
    forwardInfo.put("useCase", "Внутренние перенаправления внутри приложения");

    Map<String, Object> forwardEndpointsInfo = new LinkedHashMap<>();
    forwardEndpointsInfo.put("/api/forward/to-welcome", "Forward к welcome");
    forwardEndpointsInfo.put("/api/forward/with-attributes", "Forward с атрибутами");
    forwardEndpointsInfo.put("/api/forward/chain", "Цепочка forward");
    forwardEndpointsInfo.put("/api/forward/loop", "Демонстрация защиты от loop");
    data.put("forward", forwardInfo);

    Map<String, Object> redirectInfo = new LinkedHashMap<>();
    redirectInfo.put("description", "Перенаправление происходит на клиенте, браузер делает новый запрос");
    redirectInfo.put("url", "Меняется в браузере");
    redirectInfo.put("attributes", "Атрибуты запроса теряются");
    redirectInfo.put("performance", "Медленнее, т.к. есть round-trip");
    redirectInfo.put("useCase", "Перенаправление на другие приложения или после POST");

    Map<String, Object> redirectEndpointsInfo = new LinkedHashMap<>();
    redirectEndpointsInfo.put("/api/redirect/temporary", "Временный редирект (302)");
    redirectEndpointsInfo.put("/api/redirect/permanent", "Постоянный редирект (301)");
    redirectEndpointsInfo.put("/api/redirect/see-other", "See Other (303)");
    data.put("redirect", redirectInfo);

    Map<String, Object> redirectCodesInfo = new LinkedHashMap<>();
    redirectCodesInfo.put("301", "SC_MOVED_PERMANENTLY");
    redirectCodesInfo.put("302", "SC_FOUND");
    redirectCodesInfo.put("303", "SC_SEE_OTHER");
    redirectCodesInfo.put("307", "SC_TEMPORARY_REDIRECT");
    redirectCodesInfo.put("308", "SC_PERMANENT_REDIRECT");
    data.put("redirectCodes", redirectCodesInfo);

    return new ApiModels.HttpResponse(200, "Forward vs Redirect", data);
  }

  @GetMapping("/error-handling")
  public ApiModels.HttpResponse errorHandling() {
    Map<String, Object> data = new LinkedHashMap<>();

    data.put("description", "Обработка ошибок в сервлетах");

    Map<String, Object> methodsInfo = new LinkedHashMap<>();
    methodsInfo.put("response.setStatus(code)", "Установить статус код");
    methodsInfo.put("response.sendError(code, message)", "Отправить ошибку");
    methodsInfo.put("throw new Exception()", "Бросить исключение");
    data.put("methods", methodsInfo);

    Map<String, Object> statusCodesInfo = new LinkedHashMap<>();
    statusCodesInfo.put("4xx", "Client errors (ошибка клиента)");
    statusCodesInfo.put("5xx", "Server errors (ошибка сервера)");

    Map<String, Object> clientErrorsInfo = new LinkedHashMap<>();
    clientErrorsInfo.put("400", "Bad Request — неверный запрос");
    clientErrorsInfo.put("401", "Unauthorized — требуется аутентификация");
    clientErrorsInfo.put("403", "Forbidden — нет прав");
    clientErrorsInfo.put("404", "Not Found — ресурс не найден");
    clientErrorsInfo.put("405", "Method Not Allowed — неверный метод");

    Map<String, Object> serverErrorsInfo = new LinkedHashMap<>();
    serverErrorsInfo.put("500", "Internal Server Error — ошибка сервера");
    serverErrorsInfo.put("503", "Service Unavailable — сервис недоступен");
    data.put("statusCodes", statusCodesInfo);

    Map<String, Object> endpointsInfo = new LinkedHashMap<>();
    endpointsInfo.put("GET /api/error/bad-request", "400 Bad Request");
    endpointsInfo.put("GET /api/error/not-found", "404 Not Found");
    endpointsInfo.put("GET /api/error/forbidden", "403 Forbidden");
    endpointsInfo.put("GET /api/error/server-error", "500 Internal Server Error");
    endpointsInfo.put("GET /api/error/runtime", "Throws RuntimeException");
    endpointsInfo.put("GET /api/error/illegal-argument", "Throws IllegalArgumentException");
    endpointsInfo.put("GET /api/error/null-pointer", "Throws NullPointerException");
    endpointsInfo.put("POST /api/error", "Обработка ошибок при POST");
    data.put("endpoints", endpointsInfo);

    return new ApiModels.HttpResponse(200, "Error Handling", data);
  }
}
