package com.example.springcontext.service;

import com.example.springcontext.config.LifecycleBean;
import com.example.springcontext.listener.ContextAwareBean;
import com.example.springcontext.listener.CustomEventListener;
import com.example.springcontext.listener.EventPublisher;
import com.example.springcontext.model.ApiModels.*;
import com.example.springcontext.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Демо-сервис для демонстрации возможностей Spring Context.
 * Собирает результаты различных демонстраций в единые DTO для тестов.
 */
@Service
public class SpringContextDemoService {

    private final ApplicationContext applicationContext;
    private final Environment environment;
    private final ProductService productService;
    private final LifecycleBean lifecycleBean;
    private final ContextAwareBean contextAwareBean;
    private final EventPublisher eventPublisher;

    @Value("${app.name:Spring Context Demo}")
    private String appName;

    @Value("${app.default-price:100.00}")
    private BigDecimal defaultPrice;

    @Value("${app.feature-enabled:false}")
    private boolean featureEnabled;

    @Autowired
    public SpringContextDemoService(
            ApplicationContext applicationContext,
            Environment environment,
            ProductService productService,
            LifecycleBean lifecycleBean,
            ContextAwareBean contextAwareBean,
            EventPublisher eventPublisher) {
        this.applicationContext = applicationContext;
        this.environment = environment;
        this.productService = productService;
        this.lifecycleBean = lifecycleBean;
        this.contextAwareBean = contextAwareBean;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Демонстрация IoC контейнера.
     */
    public IocContainerResult iocContainerDemo() {
        String containerType = applicationContext.getClass().getSimpleName();

        List<String> beanNames = Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(name -> name.startsWith("product") || name.startsWith("category"))
                .sorted()
                .limit(10)
                .toList();

        String productBeanClass = productService.getClass().getSimpleName();
        String categoryBeanClass = applicationContext.containsBean("categoryService")
                ? applicationContext.getBean("categoryService").getClass().getSimpleName()
                : null;

        return new IocContainerResult(containerType, beanNames, productBeanClass, categoryBeanClass);
    }

    /**
     * Демонстрация Dependency Injection.
     */
    public DependencyInjectionResult dependencyInjectionDemo() {
        boolean constructorInjection = true;
        boolean setterInjection = productService.getDefaultCategory() != null;
        boolean fieldInjection = productService.getDefaultProduct() != null;

        String productServiceBean = productService.getClass().getSimpleName();
        String categoryServiceBean = applicationContext.containsBean("categoryService")
                ? applicationContext.getBean("categoryService").getClass().getSimpleName()
                : null;

        return new DependencyInjectionResult(
                constructorInjection,
                setterInjection,
                fieldInjection,
                productServiceBean,
                categoryServiceBean
        );
    }

    /**
     * Демонстрация Scope (singleton vs prototype).
     */
    public ScopeDemoResult scopeDemo() {
        // Singleton — один и тот же экземпляр
        Product singletonProduct1 = applicationContext.getBean(ProductService.class).getDefaultProduct();
        Product singletonProduct2 = applicationContext.getBean(ProductService.class).getDefaultProduct();

        // Prototype — каждый раз новый экземпляр
        Product prototypeProduct1 = applicationContext.getBean(PrototypeProductService.class).getCurrentProduct();
        Product prototypeProduct2 = applicationContext.getBean(PrototypeProductService.class).getCurrentProduct();

        return new ScopeDemoResult(
                singletonProduct1 != null ? singletonProduct1.getId() : null,
                singletonProduct2 != null ? singletonProduct2.getId() : null,
                prototypeProduct1 != null ? prototypeProduct1.getId() : null,
                prototypeProduct2 != null ? prototypeProduct2.getId() : null,
                singletonProduct1 != null ? singletonProduct1.getName() : null,
                singletonProduct1 != null ? singletonProduct1.getName() : null
        );
    }

    /**
     * Демонстрация жизненного цикла бина.
     */
    public LifecycleResult lifecycleDemo() {
        List<String> events = lifecycleBean.getLifecycleEvents();
        String beanName = applicationContext.getBeanNamesForType(LifecycleBean.class)[0];

        return new LifecycleResult(
                events,
                beanName,
                lifecycleBean.getInitCallCount(),
                lifecycleBean.getDestroyCallCount()
        );
    }

    /**
     * Демонстрация конфигурации через @Bean.
     */
    public ConfigurationResult configurationDemo() {
        String configuredBeanName = "smartphoneProduct";
        String productServiceType = productService.getClass().getSimpleName();
        String categoryServiceType = applicationContext.containsBean("categoryService")
                ? applicationContext.getBean("categoryService").getClass().getSimpleName()
                : null;

        List<String> allBeanNames = Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(name -> name.startsWith("product") || name.startsWith("category") || name.startsWith("default"))
                .sorted()
                .limit(10)
                .toList();

        return new ConfigurationResult(
                configuredBeanName,
                productServiceType,
                categoryServiceType,
                allBeanNames
        );
    }

    /**
     * Демонстрация стереотипов (@Component, @Service, @Repository).
     */
    public StereotypesResult stereotypesDemo() {
        String productServiceType = productService.getClass().getSimpleName();
        String categoryRepositoryType = applicationContext.containsBean("categoryRepository")
                ? applicationContext.getBean("categoryRepository").getClass().getSimpleName()
                : null;
        String orderServiceType = applicationContext.containsBean("orderService")
                ? applicationContext.getBean("orderService").getClass().getSimpleName()
                : null;

        List<String> allComponentNames = Arrays.stream(
                        applicationContext.getBeanNamesForAnnotation(org.springframework.stereotype.Component.class))
                .sorted()
                .limit(10)
                .collect(Collectors.toList());

        return new StereotypesResult(
                productServiceType,
                categoryRepositoryType,
                orderServiceType,
                allComponentNames
        );
    }

    /**
     * Демонстрация @Autowired с @Qualifier.
     */
    public QualifierResult qualifierDemo() {
        String primaryServiceName = applicationContext.getBean(ProductService.class).getClass().getSimpleName();
        String secondaryServiceName = applicationContext.containsBean("defaultProduct")
                ? applicationContext.getBean("defaultProduct").getClass().getSimpleName()
                : null;

        return new QualifierResult(
                primaryServiceName,
                secondaryServiceName,
                primaryServiceName
        );
    }

    /**
     * Демонстрация @Value для инъекции свойств.
     */
    public PropertyInjectionResult propertyInjectionDemo() {
        String activeProfile = Arrays.stream(environment.getActiveProfiles())
                .findFirst()
                .orElse("default");

        return new PropertyInjectionResult(
                appName,
                defaultPrice,
                featureEnabled,
                activeProfile
        );
    }

    /**
     * Демонстрация ApplicationContextAware.
     */
    public ContextAwareResult contextAwareDemo() {
        return new ContextAwareResult(
                contextAwareBean.getApplicationContextClassName(),
                contextAwareBean.getBeanCount(),
                contextAwareBean.containsProductService()
        );
    }

    /**
     * Демонстрация профилей.
     */
    public ProfileResult profileDemo() {
        List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
        List<String> availableProfiles = Arrays.asList(environment.getDefaultProfiles());

        String dataSourceBean = null;
        if (activeProfiles.contains("dev")) {
            dataSourceBean = applicationContext.containsBean("devProduct") ? "devProduct" : null;
        } else if (activeProfiles.contains("prod")) {
            dataSourceBean = applicationContext.containsBean("prodProduct") ? "prodProduct" : null;
        } else if (activeProfiles.contains("test")) {
            dataSourceBean = applicationContext.containsBean("testProduct") ? "testProduct" : null;
        }

        return new ProfileResult(
                activeProfiles.isEmpty() ? "default" : String.join(",", activeProfiles),
                availableProfiles,
                dataSourceBean
        );
    }

    /**
     * Демонстрация обработки событий.
     */
    public EventResult eventDemo() {
        eventPublisher.publishEvent("Test event from demo service");

        CustomEventListener listener = applicationContext.getBean(CustomEventListener.class);

        return new EventResult(
                listener.getCapturedEvents(),
                listener.getCustomEventCount(),
                listener.getLastEventPayload()
        );
    }

    /**
     * Демонстрация условных бинов (@Conditional).
     */
    public ConditionalResult conditionalDemo() {
        List<String> activeBeans = new ArrayList<>();
        List<String> missingBeans = new ArrayList<>();

        if (applicationContext.containsBean("enabledFeatureService")) {
            activeBeans.add("enabledFeatureService");
        } else {
            missingBeans.add("enabledFeatureService");
        }

        if (applicationContext.containsBean("devToolsService")) {
            activeBeans.add("devToolsService");
        } else {
            missingBeans.add("devToolsService");
        }

        String conditionType = featureEnabled ? "FeatureEnabled" : "FeatureDisabled";

        return new ConditionalResult(activeBeans, missingBeans, conditionType);
    }
}
