package org.example;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

@Singleton
public class AppInfoService {

    @Value("${micronaut.application.version}")
    private String version;

    @Value("${micronaut.application.name}")
    private String name;

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }
}
