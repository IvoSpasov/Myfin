package com.p3.myfin.api;

import com.p3.myfin.config.AppProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {
    private final AppProperties appProperties;

    public HealthController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @GetMapping
    public Map<String, Object> health() {
        return Map.of("status", "UP",
                "time", System.currentTimeMillis(),
                "seeding", appProperties.isSeeding(),
                "app", appProperties.getName());
    }
}
