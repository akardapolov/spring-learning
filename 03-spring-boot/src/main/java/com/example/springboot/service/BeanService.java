package com.example.springboot.service;

import com.example.springboot.exception.BeanNotFoundException;
import com.example.springboot.model.ApiModels.BeanInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Service for bean inspection.
 *
 * Demonstrates:
 * - Accessing ApplicationContext
 * - Bean introspection
 * - Understanding bean scopes
 */
@Slf4j
@Service
public class BeanService {

    @Autowired
    private ApplicationContext applicationContext;

    public List<BeanInfo> getAllBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        List<BeanInfo> beans = new ArrayList<>();

        for (String beanName : beanNames) {
            try {
                Object bean = applicationContext.getBean(beanName);
                String scope = "singleton"; // default scope
                if (applicationContext instanceof ConfigurableApplicationContext cac) {
                    var definition = cac.getBeanFactory().getBeanDefinition(beanName);
                    scope = definition.getScope();
                    if (scope == null || scope.isEmpty()) {
                        scope = "singleton";
                    }
                }
                beans.add(BeanInfo.builder()
                        .name(beanName)
                        .type(bean.getClass().getName())
                        .scope(scope)
                        .isSingleton(applicationContext.isSingleton(beanName))
                        .isPrototype(applicationContext.isPrototype(beanName))
                        .build());
            } catch (Exception e) {
                log.debug("Could not inspect bean: {}", beanName);
            }
        }

        return beans.stream()
                .sorted(Comparator.comparing(BeanInfo::getName))
                .toList();
    }

    public BeanInfo getBeanInfo(String beanName) {
        if (!applicationContext.containsBean(beanName)) {
            throw new BeanNotFoundException(beanName);
        }

        Object bean = applicationContext.getBean(beanName);
        String scope = "singleton"; // default scope
        if (applicationContext instanceof ConfigurableApplicationContext cac) {
            var definition = cac.getBeanFactory().getBeanDefinition(beanName);
            scope = definition.getScope();
            if (scope == null || scope.isEmpty()) {
                scope = "singleton";
            }
        }
        return BeanInfo.builder()
                .name(beanName)
                .type(bean.getClass().getName())
                .scope(scope)
                .isSingleton(applicationContext.isSingleton(beanName))
                .isPrototype(applicationContext.isPrototype(beanName))
                .build();
    }

    public long getBeanCount() {
        return applicationContext.getBeanDefinitionNames().length;
    }

    public List<String> getBeanNamesByType(Class<?> type) {
        return Arrays.asList(applicationContext.getBeanNamesForType(type));
    }
}
