package com.khoadoan.basic.demoswp.service.Impl;

import com.khoadoan.basic.demoswp.entity.AuditLog;
import com.khoadoan.basic.demoswp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.khoadoan.basic.demoswp.repository.AuditLogRepository;
import com.khoadoan.basic.demoswp.repository.UserRepository;
import com.khoadoan.basic.demoswp.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;
    private final String uploadDir = "uploads/";

    @Override
    public User register(User user, MultipartFile personalIdImage, MultipartFile licenseImage) throws IOException {
        // Check duplicate email
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already registered");
        }

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

    @Override
    public User verifyUser(Integer userId, User staff) {
        User user = userRepository.findById(userId).
            orElseThrow(() -> new RuntimeException("User not found"));
        // Chỉ verify nếu đang Suspended
        if (!"Suspended".equals(user.getStatus())) {
            throw new RuntimeException("Cannot verify user with status: " + user.getStatus());
        }
        user.setStatus("Active");
        logAudit(staff, "Verified user " + userId);
        return userRepository.save(user);
    }

    private String saveFile(MultipartFile file) throws IOException {
        // Create directory if not exists
        Files.createDirectories(Paths.get(uploadDir));

        // Generate safe filename
        String originalFileName = file.getOriginalFilename();
        String safeFileName = System.currentTimeMillis() + "_" +
                (originalFileName != null ?
                        originalFileName.replaceAll("[^a-zA-Z0-9.-]", "_") : "file");

        Path path = Paths.get(uploadDir + safeFileName);
        Files.write(path, file.getBytes());

        return safeFileName;
    }

    @Override
    public void logAudit(User user, String action) {
        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Map<String, Object> login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Invalid email or password");
        }

        // Simple password validation (in production, use proper password hashing)
        if (!password.equals(user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }
        if(!user.getStatus().equals("Active")) {
            throw new RuntimeException("User account is not active");
        }
        Map<String,Object> response = new HashMap<>();
        response.put("user", sanitizeUserData(user));  //Chưa che thông tin nhạy cảm
        response.put("message", "Login successful");

        logAudit(user, "User logged in");
        return response;
    }

    private User sanitizeUserData(User user) {
        User sanitized = new User();
        sanitized.setUserId(user.getUserId());
        sanitized.setName(user.getName());
        sanitized.setEmail(user.getEmail());
        sanitized.setPhone(user.getPhone());
        sanitized.setAddress(user.getAddress());
        sanitized.setRole(user.getRole());
        sanitized.setStatus(user.getStatus());
        sanitized.setCreatedAt(user.getCreatedAt());
        // Don't include password hash, personal documents, etc.
        return sanitized;
    }

    //Cho update name, phone, address, email
    @Override
    public User updateUser(Integer userId, User userUpdates) {
        User existingUser = userRepository.findById(userId).
                orElseThrow(() -> new RuntimeException("User not found"));
        if(userUpdates.getName() != null) {
            existingUser.setName(userUpdates.getName());
        }

        if(userUpdates.getPhone() != null) {
            existingUser.setPhone(userUpdates.getPhone());
        }
        if(userUpdates.getAddress() != null) {
            existingUser.setAddress(userUpdates.getAddress());
        }
        if(userUpdates.getEmail() != null) {
            existingUser.setEmail(userUpdates.getEmail());
        }
        User updatedUser = userRepository.save(existingUser);
        logAudit(existingUser, "Updated user " + userId);
        return updatedUser;
    }

    @Override
    public Map<String, Object> getAllUsers(int page, int size, String role, String status) {
        List<User> allUsers = userRepository.findAll();

        List<User> filteredUsers = new ArrayList<>();
        for(User user : allUsers) {
            if ((role == null ||  user.getRole().equals(role)) && (status == null || user.getStatus().equals(status))) {
                filteredUsers.add(user);
            }
        }
        int totalCount = filteredUsers.size();
        int start = page * size;
        int end = Math.min(start + size, totalCount);
        List<User> paginatedUsers = filteredUsers.subList(start, end);

        Map<String, Object> response = new HashMap<>();
        response.put("users", paginatedUsers);
        response.put("totalCount", totalCount);
        response.put("page", page);
        response.put("size", size);
        double totalPages = Math.ceil((double) totalCount / size);
        response.put("totalPages", (int) totalPages);

        return response;
    }

    @Override
    public List<User> getRiskUsers() {
        List<User> allUsers = userRepository.findAll();
        List<User> filteredUsers = new ArrayList<>();
        for(User user : allUsers) {
            if(user.getRole().equals("Customer") && !user.getStatus().equals("Active")) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }

    @Override
    public User updateUserStatus(Integer userId, String status, String reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (status == null || (!status.equals("Active") && !status.equals("Suspended") && !status.equals("Deleted"))) {
            throw new RuntimeException("Invalid status. Must be: Active, Suspended, Deleted");
        }

        user.setStatus(status);
        User updatedUser = userRepository.save(user);
        logAudit(updatedUser, "Updated user " + userId + " status to " + status + (reason != null ? " Reason: " + reason : ""));
        return updatedUser;
    }

    @Override
    public User createStaff(User staff) {
        if (staff.getName() == null || staff.getName().trim().isEmpty()) {
            throw new RuntimeException("Staff name is required");
        }
        if (staff.getEmail() == null || staff.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Staff email is required");
        }
        if (staff.getStation() == null || staff.getStation().getStationId() == null) {
            throw new RuntimeException("Station assignment is required for staff");
        }

        staff.setRole("Staff");
        staff.setStatus("Active");
        if (staff.getPasswordHash() == null) {
            // Generate default password (in production, send via email)
            staff.setPasswordHash("default_password_" + System.currentTimeMillis());
        }
        User savedStaff = userRepository.save(staff);

        logAudit(savedStaff, "Created staff " + savedStaff.getUserId());
        return savedStaff;
    }

    @Override
    public List<User> getStaff(Integer stationId) {
        List<User> allUsers = userRepository.findAll();
        List<User> filteredUsers = new ArrayList<>();
        for(User user : allUsers) {
            // 1. Check role
            if(!user.getRole().equals("Staff")) {
                continue;
            }
            if (stationId == null) {
                filteredUsers.add(user);
            } else {
                if (user.getStation() != null && user.getStation().getStationId().equals(stationId)) {
                    filteredUsers.add(user);
                }
            }
        }
        return filteredUsers;
    }

    @Override
    public User updateStaff(Integer staffId, User staffUpdates) {
        User existingStaff = userRepository.findById(staffId).
                orElseThrow(() -> new RuntimeException("User not found"));

        if (!existingStaff.getRole().equals("Staff")) {
            throw new RuntimeException("User is not a staff member");
        }

        if(staffUpdates.getName() != null) {
            existingStaff.setName(staffUpdates.getName());
        }

        if(staffUpdates.getPhone() != null) {
            existingStaff.setPhone(staffUpdates.getPhone());
        }
        if(staffUpdates.getAddress() != null) {
            existingStaff.setAddress(staffUpdates.getAddress());
        }
        if(staffUpdates.getEmail() != null) {
            existingStaff.setEmail(staffUpdates.getEmail());
        }

        if (staffUpdates.getStation() != null) {
            existingStaff.setStation(staffUpdates.getStation());
        }

        User updatedStaff = userRepository.save(existingStaff);
        logAudit(updatedStaff, "Updated staff " + staffId);
        return updatedStaff;
    }

    @Override
    public Map<String, Object> deleteStaff(Integer staffId) {
        User staff = userRepository.findById(staffId).
                orElseThrow(() -> new RuntimeException("User not found"));

        if (!staff.getRole().equals("Staff")) {
            throw new RuntimeException("User is not a staff member");
        }

        staff.setStatus("Deleted");
        userRepository.save(staff);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Staff deleted successfully");
        response.put("staffId", staffId);
        return response;
    }

}

