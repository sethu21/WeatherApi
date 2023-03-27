package com.weatherApi.demo.infrastructure;

import com.weatherApi.demo.domain.model.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JpaWeatherDataCacheTest {

    private JpaWeatherDataCache jpaWeatherDataCache;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jpaWeatherDataCache = new JpaWeatherDataCache(jdbcTemplate);
    }

    @Test
    void getTemperature_shouldReturnCachedData() {
        // Arrange
        Coordinates coordinates = new Coordinates(10.0, 20.0);
        Double temperature = 25.0;
        LocalDateTime time = LocalDateTime.now().minusMinutes(30);
        when(jdbcTemplate.queryForList(anyString(), anyDouble(), anyDouble()))
                .thenReturn(List.of(Map.of("temperature", temperature, "time", time)));

        // Act
        Double result = jpaWeatherDataCache.getTemperature(coordinates);

        // Assert
        assertEquals(temperature, result, 0.01);
        verify(jdbcTemplate).queryForList(anyString(), anyDouble(), anyDouble());
    }

    @Test
    void putTemperature_shouldAddDataToDatabase() {
        // Arrange
        Coordinates coordinates = new Coordinates(10.0, 20.0);
        Double temperature = 25.0;

        // Act
        jpaWeatherDataCache.putTemperature(coordinates, temperature);

        // Assert
        verify(jdbcTemplate).update(anyString(), anyDouble(), anyDouble(), any(LocalDateTime.class), anyDouble());
    }
}
