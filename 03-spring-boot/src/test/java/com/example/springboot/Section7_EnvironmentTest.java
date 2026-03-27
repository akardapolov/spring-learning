package com.example.springboot;

import com.example.springboot.service.ConfigurationService;
import com.example.springboot.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test for Environment and Configuration.
 *
 * Demonstrates:
 * - Environment access
 * - Property resolution
 * - Configuration sources
 * - Multiple property sources priority
 */
@SpringBootTest
class Section7_EnvironmentTest {

    @Autowired
    private Environment environment;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private ProfileService profileService;

    @Test
    void environmentIsAvailable() {
        assertThat(environment).isNotNull();
    }

    @Test
    void applicationNameCanBeRead() {
        String appName = environment.getProperty("app.name");
        assertThat(appName).isEqualTo("Spring Boot Demo Application");
    }

    @Test
    void applicationVersionCanBeRead() {
        String version = environment.getProperty("app.version");
        assertThat(version).isEqualTo("1.0.0");
    }

    @Test
    void welcomeMessageCanBeRead() {
        String welcome = environment.getProperty("app.message.welcome");
        assertThat(welcome).isNotNull();
        assertThat(welcome).isNotBlank();
    }

    @Test
    void propertyWithDefaultValue() {
        String value = environment.getProperty("non.existent.property", "default");
        assertThat(value).isEqualTo("default");
    }

    @Test
    void configurationServiceCanReadProperty() {
        String appName = configurationService.getProperty("app.name");
        assertThat(appName).isEqualTo("Spring Boot Demo Application");
    }

    @Test
    void configurationServiceReturnsDefaultForMissingProperty() {
        String value = configurationService.getProperty("non.existent.property", "default");
        assertThat(value).isEqualTo("default");
    }

    @Test
    void configurationSourcesAreAvailable() {
        var sources = configurationService.getConfigurationSources();
        assertThat(sources).isNotEmpty();
    }

    @Test
    void configurationSourcesContainApplicationConfig() {
        var sources = configurationService.getConfigurationSources();
        assertThat(sources).anyMatch(source ->
                source.contains("application") || source.contains("config"));
    }

    @Test
    void activeProfilesFromEnvironment() {
        String[] profiles = environment.getActiveProfiles();
        assertThat(profiles).isNotEmpty();
    }

    @Test
    void defaultProfilesAreDefined() {
        String[] defaultProfiles = environment.getDefaultProfiles();
        assertThat(defaultProfiles).isNotEmpty();
    }
}
