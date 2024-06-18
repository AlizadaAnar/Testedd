package com.alibou.security.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DatabaseConfigLogger {
    @PostConstruct
    public void logDatabaseUrl() {
        String databaseUrl = System.getenv("DATABASE_URL");
        System.out.println("Database URL: " + databaseUrl);
    }
}
