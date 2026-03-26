package com.example.springcontext.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Бин с доступом к ApplicationContext для демонстрации ApplicationContextAware.
 */
@Component
public class ContextAwareBean implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(ContextAwareBean.class);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("ApplicationContext injected into ContextAwareBean");
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public String getApplicationContextClassName() {
        return applicationContext.getClass().getSimpleName();
    }

    public int getBeanCount() {
        return applicationContext.getBeanDefinitionCount();
    }

    public boolean containsProductService() {
        return applicationContext.containsBeanDefinition("productService");
    }
}
