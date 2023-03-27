package com.weatherApi.demo.domain.port.cache;

import com.weatherApi.demo.domain.model.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WeatherDataCacheImplTest {

    private WeatherDataCache weatherDataCache;

    @BeforeEach
    void setUp() {
        weatherDataCache = new WeatherDataCacheImpl();
    }

    @Test
    void getTemperature_shouldReturnNullWhenDataIsNotInCache() {
        // Arrange
        Coordinates coordinates = new Coordinates(10.0, 20.0);

        // Act
        Double result = weatherDataCache.getTemperature(coordinates);

        // Assert
        assertNull(result);
    }

    @Test
    void putTemperature_shouldAddDataToCache() {
        // Arrange
        Coordinates coordinates = new Coordinates(10.0, 20.0);
        double temperature = 25.0;

        // Act
        weatherDataCache.putTemperature(coordinates, temperature);

        // Assert
        Double result = weatherDataCache.getTemperature(coordinates);
        assertEquals(temperature, result, 0.01);
    }

    @Test
    void getTemperature_shouldReturnCachedData() {
        // Arrange
        Coordinates coordinates = new Coordinates(10.0, 20.0);
        double temperature = 25.0;
        weatherDataCache.putTemperature(coordinates, temperature);

        // Act
        Double result = weatherDataCache.getTemperature(coordinates);

        // Assert
        assertEquals(temperature, result, 0.01);
    }
}