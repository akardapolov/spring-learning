package com.example.springcontext;

import com.example.springcontext.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест с активным test профилем.
 */
@SpringBootTest
@ActiveProfiles("test")
class Section10_ProfileTestEnvTest {

    @Autowired(required = false)
    @Qualifier("testProduct")
    private Product testProduct;

    @Test
    void shouldLoadTestProfileBean() {
        assertThat(testProduct).isNotNull();
        assertThat(testProduct.getType()).isEqualTo("TEST");
    }
}
