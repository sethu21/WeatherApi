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
    private final String apiUrl;// Base URL of the weather API
    private final String userAgent;// User agent to be used in API requests

    public YrWeatherApiClient(RestTemplate restTemplate, @Value("${weather.api.url}") String apiUrl, @Value("${weather.api.user-agent}") String userAgent) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.userAgent = userAgent;
    }

    @Override
    public double getTemperature(double latitude, double longitude) {
        HttpHeaders headers = new HttpHeaders();// Create a new HttpHeaders object to store the headers for the HTTP request
        headers.add("User-Agent", userAgent);// Set the User-Agent header for the HTTP request
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers); // Create a new HttpEntity with the headers and an empty body

        // Send a GET request to the weather API with the latitude and longitude parameters and the headers, and store the response in a ResponseEntity<String> object
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + "?lat=" + latitude + "&lon=" + longitude, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {// Check if the response status code is not 200 OK

            // Throw a runtime exception with an error message if the response status code is not 200 OK
            throw new RuntimeException("Failed to retrieve weather information from the Yr API: " + response.getStatusCode());// Throw a runtime exception with an error message if the response status code is not 200 OK

        }
        try {
            // Create a new ObjectMapper object to parse the JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            // Parse the response body into a JsonNode object
            JsonNode root = objectMapper.readTree(response.getBody());
            // Extract the temperature node from the JSON response
            JsonNode temperatureNode = root.at("/properties/timeseries/0/data/instant/details/air_temperature");
            if (temperatureNode.isMissingNode()) {// Check if the temperature node is missing

                // Throw a runtime exception with an error message if the temperature node is missing
                throw new RuntimeException("Failed to retrieve temperature information from the Yr API response.");
            }
            // Return the temperature value as a double
            return temperatureNode.asDouble();
        } catch (JsonProcessingException e) {
            // Throw a runtime exception with an error message and the original exception if there is an error parsing the JSON response
            throw new RuntimeException("Failed to parse Yr API response.", e);
        }
    }
}
