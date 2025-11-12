package com.khoadoan.basic.demoswp.controllers;

import com.khoadoan.basic.demoswp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.khoadoan.basic.demoswp.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@ModelAttribute User user,
                                         @RequestParam(value = "personalIdImage", required = false) MultipartFile personalIdImage,
                                         @RequestParam(value = "licenseImage", required = false) MultipartFile licenseImage) throws Exception {
        return ResponseEntity.ok(userService.register(user, personalIdImage, licenseImage));
    }

    @PostMapping("/users/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        return ResponseEntity.ok(userService.login(email, password));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userUpdates) {
        return ResponseEntity.ok(userService.updateUser(id, userUpdates));
    }

    @PutMapping("/users/{id}/verify")
    public ResponseEntity<User> verify(@PathVariable Integer id, @RequestBody User staff) { // Assume staff from auth
        return ResponseEntity.ok(userService.verifyUser(id, staff));
    }

    // Admin: Customer management
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(userService.getAllUsers(page, size, role, status));
    }

    @GetMapping("/users/risk")
    public ResponseEntity<List<User>> getRiskUsers() {
        return ResponseEntity.ok(userService.getRiskUsers());
    }

    @PutMapping("/users/{id}/status")
    public ResponseEntity<User> updateUserStatus(
            @PathVariable Integer id,
            @RequestParam String status,
            @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(userService.updateUserStatus(id, status, reason));
    }
}