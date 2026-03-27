package com.example.springboot;

import com.example.springboot.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for Spring Profiles.
 *
 * Demonstrates:
 * - Profile-specific configuration
 * - Using @ActiveProfiles for test
 * - Active profile detection
 * - Profile-based conditional logic
 */
@SpringBootTest
@ActiveProfiles("test")
class Section3_ProfilesTest {

    @Autowired
    private Environment environment;

    @Autowired
    private ProfileService profileService;

    @Test
    void testProfileIsActive() {
        assertThat(profileService.isProfileActive("test")).isTrue();
    }

    @Test
    void devProfileIsNotActive() {
        assertThat(profileService.isProfileActive("dev")).isFalse();
    }

    @Test
    void prodProfileIsNotActive() {
        assertThat(profileService.isProfileActive("prod")).isFalse();
    }

    @Test
    void activeProfilesCanBeRetrieved() {
        assertThat(profileService.getActiveProfiles()).contains("test");
    }

    @Test
    void currentProfileIsTest() {
        assertThat(profileService.getCurrentProfile()).isEqualTo("test");
    }

    @Test
    void environmentAcceptsTestProfile() {
        assertThat(environment.acceptsProfiles(org.springframework.core.env.Profiles.of("test"))).isTrue();
    }
}
