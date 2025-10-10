package com.group7.evr.controllers;

import com.group7.evr.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.group7.evr.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestPart("user") User user,
                                         @RequestPart(value = "personalIdImage", required = false) MultipartFile personalIdImage,
                                         @RequestPart(value = "licenseImage", required = false) MultipartFile licenseImage) throws Exception {
        return ResponseEntity.ok(userService.register(user, personalIdImage, licenseImage));
    }

    @PutMapping("/users/{id}/verify")
    public ResponseEntity<User> verify(@PathVariable Integer id, @RequestBody User staff) { // Assume staff from auth
        return ResponseEntity.ok(userService.verifyUser(id, staff));
    }
}
