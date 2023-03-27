package com.weatherApi.demo.domain.port.cache;

import com.weatherApi.demo.domain.model.Coordinates;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j

public class WeatherDataCacheImpl implements WeatherDataCache {
    private ConcurrentHashMap<Coordinates, CachedWeatherData> cache = new ConcurrentHashMap<>();
    @Override
    public Double getTemperature(Coordinates coordinates) {
        log.info("Getting weather data from cache for coordinates: {}", coordinates);
        CachedWeatherData cachedData = cache.get(coordinates);
        if (cachedData != null && cachedData.getTimestamp().plusHours(1).isAfter(LocalDateTime.now())) {
            return cachedData.getTemperature();
        }
        return null;
    }

    @Override
    public void putTemperature(Coordinates coordinates, double weatherData) {
        log.info("Putting weather data into cache for coordinates: {}", coordinates);
        cache.put(coordinates, new CachedWeatherData(weatherData, LocalDateTime.now()));
    }
}