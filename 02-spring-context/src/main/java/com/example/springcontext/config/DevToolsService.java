package com.example.springcontext.config;

/**
 * Dev tools сервис, создается только в dev профиле.
 */
public class DevToolsService {

    public String getMode() {
        return "DEVELOPMENT";
    }
}
