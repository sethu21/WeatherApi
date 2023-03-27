package com.weatherApi.demo.application;

import com.weatherApi.demo.domain.model.Coordinates;
import com.weatherApi.demo.domain.port.cache.WeatherDataCache;
import com.weatherApi.demo.domain.port.external.WeatherApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WeatherServiceImplTest {

    @Mock
    private WeatherApiClient weatherApiClient;

    @Mock
    private WeatherDataCache weatherDataCache;

    private WeatherService weatherService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        weatherService = new WeatherServiceImpl(weatherApiClient, weatherDataCache);
    }

    @Test
    public void getTemperature_returnsCachedTemperature_whenTemperatureIsCached() {
        // Arrange
        double expectedTemperature = 10.0;
        Coordinates coordinates = new Coordinates(1.0, 2.0);
        when(weatherDataCache.getTemperature(any(Coordinates.class))).thenReturn(expectedTemperature);

        // Act
        double actualTemperature = weatherService.getTemperature(coordinates.getLatitude(), coordinates.getLongitude());

        // Assert
        verify(weatherApiClient, never()).getTemperature(anyDouble(), anyDouble());
        verify(weatherDataCache, times(1)).getTemperature(coordinates);
        assertEquals(expectedTemperature, actualTemperature);
    }

    @Test
    public void getTemperature_callsApiClientAndCachesResult_whenTemperatureIsNotCached() {
        // Arrange
        double apiTemperature = 20.0;
        Coordinates coordinates = new Coordinates(1.0, 2.0);
        when(weatherDataCache.getTemperature(any(Coordinates.class))).thenReturn(null);
        when(weatherApiClient.getTemperature(anyDouble(), anyDouble())).thenReturn(apiTemperature);

        // Act
        double actualTemperature = weatherService.getTemperature(coordinates.getLatitude(), coordinates.getLongitude());

        // Assert
        verify(weatherApiClient, times(1)).getTemperature(coordinates.getLatitude(), coordinates.getLongitude());
        verify(weatherDataCache, times(1)).putTemperature(coordinates, apiTemperature);
        assertEquals(apiTemperature, actualTemperature);
    }
}
