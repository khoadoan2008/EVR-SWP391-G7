package com.group7.evr.service;

import com.group7.evr.dto.UserRegistrationRequest;
import com.group7.evr.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {

    public User register(UserRegistrationRequest request,
                         MultipartFile personalIdImage,
                         MultipartFile licenseImage) throws IOException;

    public User verifyEmailToken(String token);

    public User verifyUser(Integer userId, User staff);

    public User getUserById(Integer userId);

    public Map<String, Object> login(String email, String password);

    public User updateUser(Integer userId, User userUpdates);

    public void changePassword(Integer userId, String currentPassword, String newPassword);

    public Map<String, Object> getAllUsers(int page, int size, String role, String status);

    public List<User> getRiskUsers();

    public User updateUserStatus(Integer userId, String status, String reason);

    public User createStaff(User staff);

    public List<User> getStaff(Integer stationId);

    public User getPrimaryStaffForStation(Integer stationId);

    public User updateStaff(Integer staffId, User staffUpdates);

    public Map<String, Object> deleteStaff(Integer staffId);

    public void logAudit(User user, String action);
}