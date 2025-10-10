package com.group7.evr.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String name;
    private Date dateOfBirth;
    private String email;
    private String phone;
    private String passwordHash;
    private String address;
    private String personalIdImage;
    private String licenseImage;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String role; // 'Customer', 'Staff', 'Admin'
    private String status = "Active";
}
