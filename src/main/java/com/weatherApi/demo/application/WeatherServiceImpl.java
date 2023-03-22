package com.weatherApi.demo.application;

import com.weatherApi.demo.domain.model.Coordinates;
import com.weatherApi.demo.domain.model.WeatherData;
import com.weatherApi.demo.domain.port.cache.WeatherDataCache;
import com.weatherApi.demo.domain.port.external.WeatherApiClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WeatherServiceImpl implements WeatherService{
    private final WeatherApiClient weatherApiClient;
    private final WeatherDataCache weatherDataCache;

    public WeatherServiceImpl(WeatherApiClient weatherApiClient, WeatherDataCache weatherDataCache) {
        this.weatherApiClient = weatherApiClient;
        this.weatherDataCache = weatherDataCache;
    }

    @Override
    public double getTemperature(double latitude, double longitude) {
        Coordinates coordinates = new Coordinates(latitude, longitude);
        Double cachedTemperature = weatherDataCache.getTemperature(coordinates);
        if (cachedTemperature != null) {
            return cachedTemperature;
        }
        double apiTemperature = weatherApiClient.getTemperature(latitude, longitude);
        weatherDataCache.putTemperature(coordinates, apiTemperature);
        return apiTemperature;
    }

}

