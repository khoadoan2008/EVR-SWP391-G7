package com.khoadoan.basic.demoswp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;
    @ManyToOne
    @JoinColumn(name = "BookingID")
    private Booking booking;

    @Pattern(regexp = "Card|E-wallet|Cash", message = "Method must be one of: Card, E-wallet, Cash")

    private String method;
    private BigDecimal amount;
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Pattern(regexp = "Pending|Success|Failed|Refunded", message = "Status must be one of: Pending, Success, Failed, Refunded")
    private String status;

}
