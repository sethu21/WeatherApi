package com.weatherApi.demo.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates implements Serializable {
    @Column(name ="latitude")
    private Double latitude;
    @Column(name ="longitude")
    private Double longitude;
}
