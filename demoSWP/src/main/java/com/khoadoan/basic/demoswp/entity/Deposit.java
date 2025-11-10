package com.khoadoan.basic.demoswp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Deposit")
@Data
public class Deposit {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer depositId;
    @ManyToOne
    @JoinColumn(name = "BookingID")
    private Booking booking;
    private BigDecimal amount;
    @Pattern(regexp = "Held|Refunded|Forfeited",
            message = "Status must be: Held, Refunded, or Forfeited")
    private String status = "Held";
    private LocalDateTime createdAt = LocalDateTime.now();
}
