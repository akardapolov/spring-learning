package com.example.springboot.exception;

public class ConfigurationException extends AppException {

    public ConfigurationException(String key, String reason) {
        super("CONFIG_ERROR", "Configuration error for key '" + key + "': " + reason);
    }
}
