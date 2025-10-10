package com.group7.evr.controllers;

import com.group7.evr.entity.Booking;
import com.group7.evr.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.group7.evr.service.BookingService;
import com.group7.evr.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;

    // 2c. Book vehicle
    @PostMapping("/bookings")
    public ResponseEntity<Booking> createBooking(
            @RequestBody Booking booking,
            @RequestParam Integer userId) {

        User user = userService.getUserById(userId);
        return ResponseEntity.ok(bookingService.createBooking(booking, user));
    }

    // 3a. Check-in
    @PutMapping("/bookings/{id}/checkin")
    public ResponseEntity<Booking> checkIn(
            @PathVariable Integer id,
            @RequestParam Integer userId,
            @RequestBody User staff) {

        User user = userService.getUserById(userId);
        return ResponseEntity.ok(bookingService.checkIn(id, user, staff));
    }

    // 4a. Return vehicle
    @PutMapping("/bookings/{id}/return")
    public ResponseEntity<Booking> returnVehicle(
            @PathVariable Integer id,
            @RequestParam Integer userId,
            @RequestBody User staff) {

        User user = userService.getUserById(userId);
        return ResponseEntity.ok(bookingService.returnVehicle(id, user, staff));
    }

    // 5a. History
    @GetMapping("/bookings/user")
    public ResponseEntity<List<Booking>> getHistory(@RequestParam Integer userId) {
        return ResponseEntity.ok(bookingService.getUserHistory(userId));
    }

    // 5b. Analytics
    @GetMapping("/analytics/user")
    public ResponseEntity<Map<String, Object>> getAnalytics(@RequestParam Integer userId) {
        return ResponseEntity.ok(bookingService.getUserAnalytics(userId));
    }
}