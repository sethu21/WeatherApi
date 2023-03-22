package com.weatherApi.demo.adapter.output;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherApi.demo.domain.port.external.WeatherApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class YrWeatherApiClient implements WeatherApiClient {
    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String userAgent;

    public YrWeatherApiClient(RestTemplate restTemplate, @Value("${weather.api.url}") String apiUrl, @Value("${weather.api.user-agent}") String userAgent) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.userAgent = userAgent;
    }

    @Override
    public double getTemperature(double latitude, double longitude) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", userAgent);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + "?lat=" + latitude + "&lon=" + longitude, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to retrieve weather information from the Yr API: " + response.getStatusCode());
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode temperatureNode = root.at("/properties/timeseries/0/data/instant/details/air_temperature");
            if (temperatureNode.isMissingNode()) {
                throw new RuntimeException("Failed to retrieve temperature information from the Yr API response.");
            }
            return temperatureNode.asDouble();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse Yr API response.", e);
        }
    }
}
