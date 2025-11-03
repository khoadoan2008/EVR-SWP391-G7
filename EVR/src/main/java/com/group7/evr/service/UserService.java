package com.group7.evr.service;

import com.group7.evr.entity.AuditLog;
import com.group7.evr.entity.User;
import com.group7.evr.enums.UserRole;
import com.group7.evr.enums.UserStatus;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;
    private final String uploadDir = "uploads/";

    public User register(User user, MultipartFile personalIdImage, MultipartFile licenseImage) throws IOException {
        user.setRole(UserRole.CUSTOMER);
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
        user.setStatus(UserStatus.ACTIVE);
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

    // --- New: Login functionality ---
    public Map<String, Object> login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Invalid email or password");
        }
        
        // Simple password validation (in production, use proper password hashing)
        if (!password.equals(user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }
        
        if (!UserStatus.ACTIVE.equals(user.getStatus())) {
            throw new RuntimeException("Account is not active");
        }
        
        // Generate JWT token (mock implementation)
        String token = "jwt_token_" + user.getUserId() + "_" + System.currentTimeMillis();
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", sanitizeUserData(user));
        response.put("message", "Login successful");
        
        logAudit(user, "User logged in");
        return response;
    }

    // --- New: Update user functionality ---
    public User updateUser(Integer userId, User userUpdates) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Update allowed fields only
        if (userUpdates.getName() != null) {
            existingUser.setName(userUpdates.getName());
        }
        if (userUpdates.getPhone() != null) {
            existingUser.setPhone(userUpdates.getPhone());
        }
        if (userUpdates.getAddress() != null) {
            existingUser.setAddress(userUpdates.getAddress());
        }
        if (userUpdates.getEmail() != null) {
            existingUser.setEmail(userUpdates.getEmail());
        }
        
        User updatedUser = userRepository.save(existingUser);
        logAudit(updatedUser, "Updated user profile " + userId);
        return updatedUser;
    }

    // --- Helper: Sanitize user data for API responses ---
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

    // --- Admin: Customer management ---
    public Map<String, Object> getAllUsers(int page, int size, String role, String status) {
        List<User> allUsers = userRepository.findAll();
        
        // Apply filters
        List<User> filteredUsers = allUsers.stream()
                .filter(user -> role == null || user.getRole().toString().equals(role))
                .filter(user -> status == null || user.getStatus().toString().equals(status))
                .toList();
        
        // Apply pagination
        int start = page * size;
        int end = Math.min(start + size, filteredUsers.size());
        List<User> paginatedUsers = filteredUsers.subList(start, end);
        
        Map<String, Object> response = new HashMap<>();
        response.put("users", paginatedUsers);
        response.put("totalCount", filteredUsers.size());
        response.put("page", page);
        response.put("size", size);
        response.put("totalPages", (int) Math.ceil((double) filteredUsers.size() / size));
        
        return response;
    }

    public List<User> getRiskUsers() {
        return userRepository.findAll().stream()
                .filter(user -> UserRole.CUSTOMER.equals(user.getRole()))
                .filter(user -> {
                    // Check if user has risk flags (would need to implement risk flag checking)
                    // For now, return users with status != "Active"
                    return !UserStatus.ACTIVE.equals(user.getStatus());
                })
                .toList();
    }

    public User updateUserStatus(Integer userId, String status, String reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Validate status
        if (!isValidStatus(status)) {
            throw new RuntimeException("Invalid status. Must be: Active, Suspended, Banned");
        }
        
        String oldStatus = user.getStatus().toString();
        user.setStatus(UserStatus.valueOf(status.toUpperCase()));
        User updatedUser = userRepository.save(user);
        
        logAudit(updatedUser, "Updated user status from " + oldStatus + " to " + status + 
                (reason != null ? " - Reason: " + reason : ""));
        
        return updatedUser;
    }

    private boolean isValidStatus(String status) {
        return status != null && (status.equals("Active") || status.equals("Suspended") || status.equals("Banned"));
    }

    // --- Admin: Staff management ---
    public User createStaff(User staff) {
        // Validate required fields
        if (staff.getName() == null || staff.getName().trim().isEmpty()) {
            throw new RuntimeException("Staff name is required");
        }
        if (staff.getEmail() == null || staff.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Staff email is required");
        }
        if (staff.getStation() == null || staff.getStation().getStationId() == null) {
            throw new RuntimeException("Station assignment is required for staff");
        }
        
        // Set staff-specific defaults
        staff.setRole(UserRole.STAFF);
        staff.setStatus(UserStatus.ACTIVE);
        if (staff.getPasswordHash() == null) {
            // Generate default password (in production, send via email)
            staff.setPasswordHash("default_password_" + System.currentTimeMillis());
        }
        
        User savedStaff = userRepository.save(staff);
        logAudit(savedStaff, "Created staff " + savedStaff.getUserId());
        return savedStaff;
    }

    public List<User> getStaff(Integer stationId) {
        List<User> allUsers = userRepository.findAll();
        
        return allUsers.stream()
                .filter(user -> UserRole.STAFF.equals(user.getRole()))
                .filter(user -> stationId == null || (user.getStation() != null && user.getStation().getStationId().equals(stationId)))
                .toList();
    }

    public User updateStaff(Integer staffId, User staffUpdates) {
        User existingStaff = userRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        
        if (!UserRole.STAFF.equals(existingStaff.getRole())) {
            throw new RuntimeException("User is not a staff member");
        }
        
        // Update allowed fields
        if (staffUpdates.getName() != null) {
            existingStaff.setName(staffUpdates.getName());
        }
        if (staffUpdates.getPhone() != null) {
            existingStaff.setPhone(staffUpdates.getPhone());
        }
        if (staffUpdates.getAddress() != null) {
            existingStaff.setAddress(staffUpdates.getAddress());
        }
        if (staffUpdates.getEmail() != null) {
            existingStaff.setEmail(staffUpdates.getEmail());
        }
        if (staffUpdates.getStation() != null && staffUpdates.getStation().getStationId() != null) {
            existingStaff.setStation(staffUpdates.getStation());
        }
        
        User updatedStaff = userRepository.save(existingStaff);
        logAudit(updatedStaff, "Updated staff " + staffId);
        return updatedStaff;
    }

    public Map<String, Object> deleteStaff(Integer staffId) {
        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        
        if (!UserRole.STAFF.equals(staff.getRole())) {
            throw new RuntimeException("User is not a staff member");
        }
        
        // Check for active bookings/handovers (would need to implement this check)
        // For now, we'll allow deletion but log a warning
        
        // Soft delete by setting status to 'Inactive'
        staff.setStatus(UserStatus.DELETED);
        userRepository.save(staff);
        
        logAudit(staff, "Deleted staff " + staffId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Staff deleted successfully");
        response.put("staffId", staffId);
        return response;
    }

}

