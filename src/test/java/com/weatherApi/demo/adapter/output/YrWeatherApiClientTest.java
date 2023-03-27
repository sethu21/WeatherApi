package com.weatherApi.demo.adapter.output;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class YrWeatherApiClientTest {

    private RestTemplate restTemplate;
    private YrWeatherApiClient yrWeatherApiClient;

    @BeforeEach
    public void setup() {
        restTemplate = mock(RestTemplate.class);
        yrWeatherApiClient = new YrWeatherApiClient(restTemplate, "https://api.met.no/weatherapi/locationforecast/2.0/compact", "MyWeatherApp/1.0");
    }

    @Test
    public void getTemperature_returnsTemperature_whenApiReturns200() throws JsonProcessingException {
        // Arrange
        double expectedTemperature = 15.5;
        String responseBody = "{\"properties\":{\"timeseries\":[{\"data\":{\"instant\":{\"details\":{\"air_temperature\":" + expectedTemperature + "}}}}]}}";
        ResponseEntity<String> response = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.exchange(any(String.class), any(), any(), any(Class.class))).thenReturn(response);

        // Act
        double actualTemperature = yrWeatherApiClient.getTemperature(12.3, 45.6);

        // Assert
        assertEquals(expectedTemperature, actualTemperature);
    }

    @Test
    public void getTemperature_throwsRuntimeException_whenApiReturnsNon200() {
        // Arrange
        ResponseEntity<Map<Object, Object>> response = new ResponseEntity<>(Collections.emptyMap(), HttpStatus.BAD_REQUEST);
        when(restTemplate.exchange(any(String.class), any(), any(), any(Class.class))).thenReturn(response);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> yrWeatherApiClient.getTemperature(12.3, 45.6));
    }

    @Test
    public void getTemperature_throwsRuntimeException_whenTemperatureNodeIsMissing() {
        // Arrange
        String responseBody = "{\"properties\":{\"timeseries\":[{\"data\":{\"instant\":{\"details\":{\"some_other_property\":12.3}}}}]}}";
        ResponseEntity<String> response = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.exchange(any(String.class), any(), any(), any(Class.class))).thenReturn(response);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> yrWeatherApiClient.getTemperature(12.3, 45.6));
    }

    @Test
    public void getTemperature_throwsRuntimeException_whenJsonParsingFails() {
        // Arrange
        ResponseEntity<String> response = new ResponseEntity<>("Invalid JSON response", HttpStatus.OK);
        when(restTemplate.exchange(any(String.class), any(), any(), any(Class.class))).thenReturn(response);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> yrWeatherApiClient.getTemperature(12.3, 45.6));
    }
}
