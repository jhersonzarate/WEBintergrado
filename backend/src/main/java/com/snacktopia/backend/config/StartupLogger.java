package com.snacktopia.backend.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger {

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        System.out.println("\n========================================");
        System.out.println("  SNACKTOPIA BACKEND INICIADO");
        System.out.println("========================================");
        System.out.println("  API:        http://localhost:8080");
        System.out.println("  Swagger UI: http://localhost:8080/swagger-ui/index.html");
        System.out.println("  API Docs:   http://localhost:8080/api-docs");
        System.out.println("========================================\n");
    }
}