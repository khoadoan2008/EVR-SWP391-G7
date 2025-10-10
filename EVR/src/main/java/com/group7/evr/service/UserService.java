package com.group7.evr.service;

import com.group7.evr.entity.AuditLog;
import com.group7.evr.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.group7.evr.repository.AuditLogRepository;
import com.group7.evr.repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;
    private final String uploadDir = "uploads/";

    public User register(User user, MultipartFile personalIdImage, MultipartFile licenseImage) throws IOException {
        user.setRole("Customer");
        if (personalIdImage != null) {
            String fileName = saveFile(personalIdImage);
            user.setPersonalIdImage(fileName);
        }
        if (licenseImage != null) {
            String fileName = saveFile(licenseImage);
            user.setLicenseImage(fileName);
        }
        User savedUser = userRepository.save(user);
        logAudit(savedUser, "Registered user " + savedUser.getUserId());
        return savedUser;
    }

    public User verifyUser(Integer userId, User staff) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus("Active");
        logAudit(staff, "Verified user " + userId);
        return userRepository.save(user);
    }

    private String saveFile(MultipartFile file) throws IOException {
        Path path =  Paths.get(uploadDir + file.getOriginalFilename());
        Files.write(path, file.getBytes());
        return path.toString();
    }

    public void logAudit(User user, String action) {
        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }
public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
}

}

