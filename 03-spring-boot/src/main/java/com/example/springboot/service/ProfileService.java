package com.example.springboot.service;

import com.example.springboot.model.ApiModels.ProfileInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service for profile management.
 *
 * Demonstrates:
 * - Working with Spring profiles
 * - Profile-specific configuration
 * - Conditional logic based on profiles
 */
@Slf4j
@Service
public class ProfileService {

    @Autowired
    private Environment environment;

    public ProfileInfo getProfileInfo() {
        String[] activeProfiles = environment.getActiveProfiles();
        String[] defaultProfiles = environment.getDefaultProfiles();

        String currentProfile = activeProfiles.length > 0
                ? activeProfiles[0]
                : "default";

        return ProfileInfo.builder()
                .activeProfiles(Arrays.asList(activeProfiles))
                .defaultProfiles(Arrays.asList(defaultProfiles))
                .currentProfile(currentProfile)
                .build();
    }

    public boolean isProfileActive(String profile) {
        return environment.acceptsProfiles(org.springframework.core.env.Profiles.of(profile));
    }

    public List<String> getActiveProfiles() {
        return Arrays.asList(environment.getActiveProfiles());
    }

    public String getCurrentProfile() {
        String[] profiles = environment.getActiveProfiles();
        return profiles.length > 0 ? profiles[0] : "default";
    }

    public String getProfileSpecificMessage() {
        if (isProfileActive("dev")) {
            return "Running in development mode with debug enabled";
        } else if (isProfileActive("prod")) {
            return "Running in production mode with optimizations";
        } else if (isProfileActive("test")) {
            return "Running in test mode";
        }
        return "Running in default mode";
    }
}
