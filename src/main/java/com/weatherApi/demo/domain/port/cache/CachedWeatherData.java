package com.weatherApi.demo.domain.port.cache;

import java.time.LocalDateTime;

public class CachedWeatherData {
    private Double temperature;
    private LocalDateTime timestamp;

    public CachedWeatherData(Double temperature, LocalDateTime timestamp) {
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

    public Double getTemperature() {
        return temperature;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
