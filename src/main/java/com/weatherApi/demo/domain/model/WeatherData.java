package com.weatherApi.demo.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="weatherData")
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Coordinates coordinates;
    @Column(name = "time")
    private LocalDateTime time;
    @Column(name ="temperature")
    private Double temperature;

    public WeatherData(LocalDateTime time, Double temperature) {
        this.time = time;
        this.temperature = temperature;
    }

}
