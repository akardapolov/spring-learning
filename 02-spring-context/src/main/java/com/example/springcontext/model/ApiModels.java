package com.example.springcontext.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Модели для API ответов и результатов демо-сервисов.
 * Используют Java Records для иммутабельных DTO.
 */
public class ApiModels {

    private ApiModels() {
    }

    /**
     * Результат демонстрации IoC контейнера.
     */
    public record IocContainerResult(
            String containerType,
            List<String> beanNames,
            String productBeanClass,
            String categoryBeanClass
    ) {}

    /**
     * Результат демонстрации Dependency Injection.
     */
    public record DependencyInjectionResult(
            boolean constructorInjection,
            boolean setterInjection,
            boolean fieldInjection,
            String productServiceBean,
            String categoryServiceBean
    ) {}

    /**
     * Результат демонстрации Scope (singleton vs prototype).
     */
    public record ScopeDemoResult(
            String singletonProduct1Id,
            String singletonProduct2Id,
            String prototypeProduct1Id,
            String prototypeProduct2Id,
            String singletonProductName,
            String prototypeProductName
    ) {}

    /**
     * Результат демонстрации жизненного цикла бина.
     */
    public record LifecycleResult(
            List<String> events,
            String beanName,
            int initCallCount,
            int destroyCallCount
    ) {}

    /**
     * Результат демонстрации конфигурации через @Bean.
     */
    public record ConfigurationResult(
            String configuredBeanName,
            String productServiceType,
            String categoryServiceType,
            List<String> allBeanNames
    ) {}

    /**
     * Результат демонстрации стереотипов (@Component, @Service, @Repository).
     */
    public record StereotypesResult(
            String productServiceType,
            String categoryRepositoryType,
            String orderServiceType,
            List<String> allComponentNames
    ) {}

    /**
     * Результат демонстрации @Autowired с @Qualifier.
     */
    public record QualifierResult(
            String primaryServiceName,
            String secondaryServiceName,
            String selectedServiceName
    ) {}

    /**
     * Результат демонстрации @Value для инъекции свойств.
     */
    public record PropertyInjectionResult(
            String appName,
            BigDecimal defaultPrice,
            boolean featureEnabled,
            String environment
    ) {}

    /**
     * Результат демонстрации ApplicationContextAware.
     */
    public record ContextAwareResult(
            String applicationContextClass,
            int beanCount,
            boolean containsProductService
    ) {}

    /**
     * Результат демонстрации профилей.
     */
    public record ProfileResult(
            String activeProfile,
            List<String> availableProfiles,
            String dataSourceBean
    ) {}

    /**
     * Результат демонстрации обработки событий.
     */
    public record EventResult(
            List<String> capturedEvents,
            int customEventCount,
            String lastEventPayload
    ) {}

    /**
     * Результат демонстрации условных бинов (@Conditional).
     */
    public record ConditionalResult(
            List<String> activeBeans,
            List<String> missingBeans,
            String conditionType
    ) {}

    /**
     * DTO продукта для ответа.
     */
    public record ProductDto(
            String id,
            String name,
            String type,
            BigDecimal price,
            String category
    ) {}

    /**
     * DTO категории для ответа.
     */
    public record CategoryDto(
            String id,
            String title,
            String description
    ) {}

    /**
     * DTO заказа для ответа.
     */
    public record OrderDto(
            String id,
            String orderNumber,
            BigDecimal total,
            String status,
            String customerName
    ) {}
}
