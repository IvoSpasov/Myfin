package com.p3.myfin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="app")
public class AppProperties {
    private String name;
    private boolean seeding;

    public String getName() { return name; }
    public boolean isSeeding() { return seeding; }

    public void setName(String name) { this.name = name; }
    public void setSeeding(boolean seeding) { this.seeding = seeding; }
}
