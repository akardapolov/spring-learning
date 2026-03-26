package com.example.springcontext;

import com.example.springcontext.config.LifecycleBean;
import com.example.springcontext.model.ApiModels.LifecycleResult;
import com.example.springcontext.service.SpringContextDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Раздел 4: Демонстрация жизненного цикла бина в Spring.
 * Бин проходит через несколько этапов: создание, инициализация, использование, уничтожение.
 */
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section4_LifecycleTest {

    private final SpringContextDemoService service;
    private final ApplicationContext context;
    private final LifecycleBean lifecycleBean;

    @Autowired
    Section4_LifecycleTest(
            SpringContextDemoService service,
            ApplicationContext context,
            LifecycleBean lifecycleBean) {
        this.service = service;
        this.context = context;
        this.lifecycleBean = lifecycleBean;
    }

    @Test
    void shouldDemonstrateLifecycle() {
        LifecycleResult result = service.lifecycleDemo();

        // Проверка событий жизненного цикла
        assertThat(result.events()).isNotEmpty();
        assertThat(result.events()).contains("CONSTRUCTOR", "POST_CONSTRUCT");

        // Имя бина должно быть установлено
        assertThat(result.beanName()).isNotBlank();

        // @PostConstruct должен быть вызван один раз
        assertThat(result.initCallCount()).isEqualTo(1);
    }

    @Test
    void shouldCallPostConstructAfterConstructor() {
        // Проверка порядка вызовов
        var events = lifecycleBean.getLifecycleEvents();
        assertThat(events).isNotEmpty();
        assertThat(events.getFirst()).isEqualTo("CONSTRUCTOR");
        assertThat(events).contains("POST_CONSTRUCT");
    }

    @Test
    void shouldHaveCorrectBeanName() {
        String[] beanNames = context.getBeanNamesForType(LifecycleBean.class);
        assertThat(beanNames).hasSize(1);
        assertThat(beanNames[0]).isNotBlank();
    }

    @Test
    void shouldInitOnlyOnce() {
        // @PostConstruct должен быть вызван только один раз для singleton бина
        assertThat(lifecycleBean.getInitCallCount()).isEqualTo(1);
    }
}
