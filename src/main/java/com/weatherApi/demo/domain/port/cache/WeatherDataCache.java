package com.weatherApi.demo.domain.port.cache;

import com.weatherApi.demo.domain.model.Coordinates;

public interface WeatherDataCache {
    Double getTemperature(Coordinates coordinates);
    void putTemperature(Coordinates coordinates, double weatherData);
}
