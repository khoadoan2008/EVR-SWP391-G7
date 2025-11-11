package com.group7.evr.entity;

import com.group7.evr.enums.UserRole;
import com.group7.evr.enums.UserStatus;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "Role")
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private UserStatus status = UserStatus.SUSPENDED;

    // ⚠️ Đây là phần gây lỗi — phải map đúng tên foreign key trong bảng Users
    @ManyToOne
    @JoinColumn(name = "StationID") // CHÍNH XÁC tên cột trong bảng Users
    private Station station;
}

