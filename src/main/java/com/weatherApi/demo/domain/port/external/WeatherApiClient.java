package com.weatherApi.demo.domain.port.external;

import com.weatherApi.demo.domain.model.Coordinates;
import com.weatherApi.demo.domain.model.WeatherData;

public interface WeatherApiClient {
    double getTemperature(double latitude, double longitude);
}
