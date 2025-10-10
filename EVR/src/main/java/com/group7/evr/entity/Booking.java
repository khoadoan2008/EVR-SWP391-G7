package com.group7.evr.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "Booking")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;
    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "VehicleID")
    private Vehicle vehicle;
    @ManyToOne
    @JoinColumn(name = "StationID")
    private Station station;
    @ManyToOne
    @JoinColumn(name = "StaffID")
    private User staff;
    private Date startTime;
    private Date endTime;
    private BigDecimal totalPrice;
    private String bookingStatus = "Pending";
}
