package com.group7.evr.service;

import com.group7.evr.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {
    // USER REGISTRATION & AUTHENTICATION
    public User register(User user, MultipartFile personalIdImage, MultipartFile licenseImage) throws IOException;
    public User verifyUser(Integer userId, User staff);
    public Map<String, Object> login(String email, String password);

    // SECURITY & AUDITING
    public void logAudit(User user, String action);

    // USER MANAGEMENT
    public User getUserById(Integer userId);
    public User updateUser(Integer userId, User userUpdates);
    public Map<String, Object> getAllUsers(int page, int size, String role, String status);
    public List<User> getRiskUsers();
    public User updateUserStatus(Integer userId, String status, String reason);

    // STAFF MANAGEMENT (Admin functions)
    public User createStaff(User staff);
    public List<User> getStaff(Integer stationId);
    public User updateStaff(Integer staffId, User staffUpdates);
    public Map<String, Object> deleteStaff(Integer staffId);
}
