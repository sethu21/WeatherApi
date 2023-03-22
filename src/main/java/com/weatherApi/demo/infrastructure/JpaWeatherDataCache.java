package com.weatherApi.demo.infrastructure;

import com.weatherApi.demo.domain.model.Coordinates;
import com.weatherApi.demo.domain.port.cache.WeatherDataCache;
import lombok.extern.slf4j.Slf4j;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;


@Repository
@Transactional
@Slf4j
public class JpaWeatherDataCache implements WeatherDataCache {

    private final JdbcTemplate jdbcTemplate;



    public JpaWeatherDataCache(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTemperature(Coordinates coordinates) {
        log.info("Getting weather data from cache for coordinates: {}", coordinates);
        String sql = "SELECT temperature FROM weatherData WHERE latitude = ? AND longitude = ?";
        List<Double> result = jdbcTemplate.queryForList(sql, Double.class, coordinates.getLatitude(), coordinates.getLongitude());
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public void putTemperature(Coordinates coordinates, double temperature) {
        log.info("Putting weather data into cache for coordinates: {}", coordinates);
        String sql = "INSERT INTO weatherData (latitude, longitude, time, temperature) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, coordinates.getLatitude(), coordinates.getLongitude(), LocalDateTime.now(), temperature);

    }
}
