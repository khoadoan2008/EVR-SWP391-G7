package com.khoadoan.basic.demoswp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "Name")
    private String name;

    @Column(name = "DateOfBirth")
    private Date dateOfBirth;

    @Column(name = "Email")
    private String email;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "PasswordHash")
    private String passwordHash;

    @Column(name = "Address")
    private String address;

    @Column(name = "PersonalIdImage")
    private String personalIdImage;

    @Column(name = "LicenseImage")
    private String licenseImage;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "Role")
    @Pattern(regexp = "Customer|Staff|Admin", message = "Role must be Customer, Staff, or Admin")
    private String role;

    @Column(name = "Status")
    @Pattern(regexp = "Active|Suspended|Deleted", message = "Status must be Active, Suspended, or Deleted")
    private String status = "Active";

    @ManyToOne
    @JoinColumn(name = "StationID")
    private Station station;
}
