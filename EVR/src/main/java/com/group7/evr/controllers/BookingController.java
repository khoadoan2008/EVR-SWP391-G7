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

    // Get booking details
    @GetMapping("/bookings/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
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

    @GetMapping("/staff/bookings/checkin-queue")
    public ResponseEntity<List<Booking>> getCheckInQueue(@RequestParam Integer staffId) {
        return ResponseEntity.ok(bookingService.getCheckInQueue(staffId));
    }

    @GetMapping("/staff/bookings/return-queue")
    public ResponseEntity<List<Booking>> getReturnQueue(@RequestParam Integer staffId) {
        return ResponseEntity.ok(bookingService.getReturnQueue(staffId));
    }

    // 5a. History
    @GetMapping("/bookings/user")
    public ResponseEntity<List<Booking>> getHistory(@RequestParam Integer userId) {
        return ResponseEntity.ok(bookingService.getUserHistory(userId));
    }

    // Enhanced user booking history with pagination and filtering
    @GetMapping("/bookings/user/{userId}")
    public ResponseEntity<Map<String, Object>> getUserBookings(
            @PathVariable Integer userId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(bookingService.getUserBookingsWithFilters(userId, status, fromDate, toDate, page, size));
    }

    // 5b. Analytics
    @GetMapping("/analytics/user")
    public ResponseEntity<Map<String, Object>> getAnalytics(@RequestParam Integer userId) {
        return ResponseEntity.ok(bookingService.getUserAnalytics(userId));
    }

    // Advanced analytics
    @GetMapping("/analytics/user/advanced")
    public ResponseEntity<Map<String, Object>> getAdvancedAnalytics(@RequestParam Integer userId) {
        return ResponseEntity.ok(bookingService.getAdvancedUserAnalytics(userId));
    }

    // New: modify booking
    @PutMapping("/bookings/{id}")
    public ResponseEntity<Booking> modifyBooking(
            @PathVariable Integer id,
            @RequestParam Integer userId,
            @RequestBody Booking updates) {
        User actor = userService.getUserById(userId);
        return ResponseEntity.ok(bookingService.modifyBooking(id, updates, actor));
    }

    // New: cancel booking
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Booking> cancelBooking(
            @PathVariable Integer id,
            @RequestParam Integer userId) {
        User actor = userService.getUserById(userId);
        return ResponseEntity.ok(bookingService.cancelBooking(id, actor));
    }

    // New: settlement
    @PostMapping("/bookings/{id}/settlement")
    public ResponseEntity<Map<String, Object>> settle(
            @PathVariable Integer id,
            @RequestParam Integer userId) {
        User actor = userService.getUserById(userId);
        return ResponseEntity.ok(bookingService.settleBooking(id, actor));
    }

    // Placeholder: return inspection upload (extend later with multipart)
    @PostMapping("/bookings/{id}/return-inspection")
    public ResponseEntity<String> uploadReturnInspection(
            @PathVariable Integer id,
            @RequestParam Integer userId) {
        userService.getUserById(userId);
        // TODO: implement with file uploads & checklist
        return ResponseEntity.ok("Return inspection received for booking " + id);
    }
}