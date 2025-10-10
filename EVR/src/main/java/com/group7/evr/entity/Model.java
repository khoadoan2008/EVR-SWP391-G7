package com.group7.evr.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "Model")
@Data
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer modelId;
    private String brand;
    private String modelName;
    private String vehicleType;
    private BigDecimal basePrice;
    private String features;
}
