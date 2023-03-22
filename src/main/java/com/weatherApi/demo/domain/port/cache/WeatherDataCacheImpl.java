package com.weatherApi.demo.domain.port.cache;

import com.weatherApi.demo.domain.model.Coordinates;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class WeatherDataCacheImpl implements WeatherDataCache{
    private ConcurrentHashMap<Coordinates, Double> cache = new ConcurrentHashMap<>();

    @Override
    public Double getTemperature(Coordinates coordinates) {
        log.info("Getting weather data from cache for coordinates: {}", coordinates);
        return cache.get(coordinates);
    }

    @Override
    public void putTemperature(Coordinates coordinates, double weatherData) {
        log.info("Putting weather data into cache for coordinates: {}", coordinates);
        cache.put(coordinates, weatherData);
    }
}
