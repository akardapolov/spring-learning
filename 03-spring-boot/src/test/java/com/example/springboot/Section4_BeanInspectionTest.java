package com.example.springboot;

import com.example.springboot.service.BeanService;
import com.example.springboot.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test for Bean Inspection.
 *
 * Demonstrates:
 * - ApplicationContext access
 * - Bean introspection
 * - Bean scope understanding
 * - Singleton vs Prototype beans
 */
@SpringBootTest
class Section4_BeanInspectionTest {

    @Autowired
    private BeanService beanService;

    @Autowired
    private ProfileService profileService;

    @Test
    void applicationContextHasBeans() {
        assertThat(beanService.getBeanCount()).isGreaterThan(0);
    }

    @Test
    void beanServiceBeanIsPresent() {
        var beans = beanService.getBeanNamesByType(BeanService.class);
        assertThat(beans).isNotEmpty();
    }

    @Test
    void profileServiceBeanIsPresent() {
        var beans = beanService.getBeanNamesByType(ProfileService.class);
        assertThat(beans).isNotEmpty();
    }

    @Test
    void allBeansHaveNames() {
        var beans = beanService.getAllBeans();
        assertThat(beans).allMatch(bean -> bean.getName() != null && !bean.getName().isBlank());
    }

    @Test
    void allBeansHaveTypes() {
        var beans = beanService.getAllBeans();
        assertThat(beans).allMatch(bean -> bean.getType() != null && !bean.getType().isBlank());
    }

    @Test
    void beanServiceIsSingleton() {
        var beanInfo = beanService.getBeanInfo("beanService");
        assertThat(beanInfo.isSingleton()).isTrue();
        assertThat(beanInfo.isPrototype()).isFalse();
    }

    @Test
    void getBeanInfoForValidBean() {
        var beanInfo = beanService.getBeanInfo("profileService");
        assertThat(beanInfo.getName()).isEqualTo("profileService");
        assertThat(beanInfo.getType()).contains("ProfileService");
    }

    @Test
    void getBeanInfoThrowsForInvalidBean() {
        assertThatThrownBy(() -> beanService.getBeanInfo("nonExistentBean"))
                .hasMessageContaining("not found");
    }
}
