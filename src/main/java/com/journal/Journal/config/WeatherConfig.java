package com.journal.Journal.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "weather.api")
public class WeatherConfig {
    private String key;
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
}
