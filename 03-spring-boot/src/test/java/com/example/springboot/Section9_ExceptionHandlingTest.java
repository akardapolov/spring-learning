package com.example.springboot;

import com.example.springboot.exception.BeanNotFoundException;
import com.example.springboot.exception.ConfigurationException;
import com.example.springboot.service.BeanService;
import com.example.springboot.service.ConfigurationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test for Exception Handling.
 *
 * Demonstrates:
 * - Custom exceptions
 * - Exception throwing in services
 * - @RestControllerAdvice usage
 * - Validation exception handling
 */
@SpringBootTest
class Section9_ExceptionHandlingTest {

    @Autowired
    private BeanService beanService;

    @Autowired
    private ConfigurationService configurationService;

    @Test
    void getNonExistentBeanThrowsException() {
        assertThatThrownBy(() -> beanService.getBeanInfo("nonExistentBean"))
                .isInstanceOf(BeanNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void beanNotFoundExceptionHasCorrectCode() {
        try {
            beanService.getBeanInfo("nonExistentBean");
        } catch (BeanNotFoundException e) {
            assertThat(e.getCode()).isEqualTo("BEAN_NOT_FOUND");
        }
    }

    @Test
    void getMissingPropertyThrowsException() {
        assertThatThrownBy(() -> configurationService.getProperty("non.existent.property"))
                .isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void configurationExceptionHasCorrectCode() {
        try {
            configurationService.getProperty("non.existent.property");
        } catch (ConfigurationException e) {
            assertThat(e.getCode()).isEqualTo("CONFIG_ERROR");
        }
    }

    @Test
    void getValidPropertyDoesNotThrow() {
        String value = configurationService.getProperty("app.name");
        assertThat(value).isEqualTo("Spring Boot Demo Application");
    }

    @Test
    void getMissingPropertyWithDefaultDoesNotThrow() {
        String value = configurationService.getProperty("non.existent.property", "default");
        assertThat(value).isEqualTo("default");
    }

    @Test
    void getValidBeanDoesNotThrow() {
        var beanInfo = beanService.getBeanInfo("beanService");
        assertThat(beanInfo).isNotNull();
    }
}
