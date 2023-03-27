package com.weatherApi.demo.adapter.input;

import com.weatherApi.demo.application.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeatherControllerTest {

    private WeatherController controller;
    private WeatherService weatherService;

    @BeforeEach
    public void setUp() {
        weatherService = mock(WeatherService.class);
        controller = new WeatherController(weatherService);
    }

    @Test
    public void testGetWeather() {
        // Arrange
        double expectedTemperature = 15.5;
        when(weatherService.getTemperature(anyDouble(), anyDouble())).thenReturn(expectedTemperature);
        Map<String, Double> expectedResponse = new HashMap<>();
        expectedResponse.put("temperature", expectedTemperature);

        // Act
        ResponseEntity<Map<String, Double>> response = controller.getWeather(12.3, 45.6);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}
