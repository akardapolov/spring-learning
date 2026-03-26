package com.example.springcontext.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Демонстрация жизненного цикла бина с @PostConstruct и @PreDestroy.
 */
@Component
public class LifecycleBean {

    private static final Logger log = LoggerFactory.getLogger(LifecycleBean.class);

    private final List<String> lifecycleEvents = new ArrayList<>();
    private int initCallCount = 0;
    private int destroyCallCount = 0;

    public LifecycleBean() {
        log.info("LifecycleBean constructor called");
        lifecycleEvents.add("CONSTRUCTOR");
    }

    @PostConstruct
    public void init() {
        initCallCount++;
        log.info("LifecycleBean @PostConstruct called (count: {})", initCallCount);
        lifecycleEvents.add("POST_CONSTRUCT");
    }

    @PreDestroy
    public void destroy() {
        destroyCallCount++;
        log.info("LifecycleBean @PreDestroy called (count: {})", destroyCallCount);
        lifecycleEvents.add("PRE_DESTROY");
    }

    public List<String> getLifecycleEvents() {
        return new ArrayList<>(lifecycleEvents);
    }

    public int getInitCallCount() {
        return initCallCount;
    }

    public int getDestroyCallCount() {
        return destroyCallCount;
    }
}
