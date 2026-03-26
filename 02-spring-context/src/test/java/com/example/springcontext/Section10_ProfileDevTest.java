package com.example.springcontext;

import com.example.springcontext.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест с активным dev профилем.
 */
@SpringBootTest
@ActiveProfiles("dev")
class Section10_ProfileDevTest {

    @Autowired(required = false)
    @Qualifier("devProduct")
    private Product devProduct;

    @Test
    void shouldLoadDevProfileBean() {
        assertThat(devProduct).isNotNull();
        assertThat(devProduct.getType()).isEqualTo("DEV");
    }
}
