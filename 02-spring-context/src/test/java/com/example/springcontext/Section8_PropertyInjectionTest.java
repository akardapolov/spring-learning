package com.example.springcontext;

import com.example.springcontext.model.ApiModels.PropertyInjectionResult;
import com.example.springcontext.service.ProductService;
import com.example.springcontext.service.SpringContextDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestConstructor;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Раздел 8: Демонстрация инъекции свойств через @Value.
 * @Value позволяет инъектировать значения из properties файлов, переменных окружения и др.
 */
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section8_PropertyInjectionTest {

    private final SpringContextDemoService service;
    private final Environment environment;
    private final ProductService productService;

    @Value("${app.name:default}")
    private String appNameFromValue;

    @Value("${app.default-price:0.00}")
    private BigDecimal defaultPriceFromValue;

    @Value("${app.feature-enabled:false}")
    private boolean featureEnabledFromValue;

    @Autowired
    Section8_PropertyInjectionTest(
            SpringContextDemoService service,
            Environment environment,
            ProductService productService) {
        this.service = service;
        this.environment = environment;
        this.productService = productService;
    }

    @Test
    void shouldDemonstratePropertyInjection() {
        PropertyInjectionResult result = service.propertyInjectionDemo();

        // Проверка, что свойства инъектированы
        assertThat(result.appName()).isNotBlank();
        assertThat(result.defaultPrice()).isNotNull();
        assertThat(result.environment()).isNotBlank();
    }

    @Test
    void shouldInjectAppProperties() {
        // Проверка через @Value
        assertThat(appNameFromValue).isNotBlank();
        assertThat(defaultPriceFromValue).isNotNull();
        assertThat(defaultPriceFromValue).isGreaterThanOrEqualTo(BigDecimal.ZERO);
    }

    @Test
    void shouldInjectIntoService() {
        // Проверка, что свойства инъектированы в сервис
        String defaultCategory = productService.getDefaultCategory();
        assertThat(defaultCategory).isNotNull();
    }

    @Test
    void shouldUseDefaultValues() {
        // Проверка использования значений по умолчанию
        // app.name не определен, поэтому должно использоваться значение по умолчанию
        assertThat(appNameFromValue).isEqualTo("default");
        assertThat(featureEnabledFromValue).isFalse();
    }

    @Test
    void shouldGetActiveProfileFromEnvironment() {
        // Получение активного профиля через Environment
        String[] activeProfiles = environment.getActiveProfiles();
        assertThat(activeProfiles).isNotNull();
    }
}
