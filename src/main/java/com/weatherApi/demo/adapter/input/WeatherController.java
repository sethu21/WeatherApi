package com.weatherApi.demo.adapter.input;

import com.weatherApi.demo.application.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public ResponseEntity<Map<String, Double>> getWeather(@RequestParam("lat") double latitude, @RequestParam("lon") double longitude) {
        double temperature = weatherService.getTemperature(latitude, longitude);
        // Create a new HashMap to store response data
        Map<String, Double> response = new HashMap<>();
        // Add temperature value to the response HashMap with key "temperature"
        response.put("temperature", temperature);
        // Return the response with HTTP status code 200 OK and the response body containing the HashMap with temperature value
        return ResponseEntity.ok(response);

    }
}
