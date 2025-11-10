package com.khoadoan.basic.demoswp.service.Impl;

import com.khoadoan.basic.demoswp.entity.Booking;
import com.khoadoan.basic.demoswp.entity.User;
import com.khoadoan.basic.demoswp.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.khoadoan.basic.demoswp.repository.BookingRepository;
import com.khoadoan.basic.demoswp.repository.VehicleRepository;
import com.khoadoan.basic.demoswp.service.BookingService;
import com.khoadoan.basic.demoswp.service.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserService userService;

    @Override
    public Booking createBooking(Booking booking, User user) {
        Vehicle vehicle = vehicleRepository.findById(booking.getVehicle().getVehicleId()).orElseThrow();

        if (!"Available".equals(vehicle.getStatus())) {
            throw new RuntimeException("Vehicle not available");
        }

        if (hasTimeConflict(booking)) {
            throw new RuntimeException("Booking time conflicts with existing booking");
        }

        if (booking.getStartTime() != null && booking.getEndTime() != null) {
            if (booking.getStartTime().after(booking.getEndTime())) {
                throw new RuntimeException("Start time cannot be after end time");
            }
        }

        vehicle.setStatus("Rented");
        vehicleRepository.save(vehicle);
        booking.setUser(user);
        booking.setBookingStatus("Pending");

        userService.logAudit(user, "Created booking " + booking.getBookingId());

        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingById(Integer bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(()-> new RuntimeException("Booking not found"));
    }

    @Override
    public Map<String, Object> getUserBookingsWithFilters(Integer userId, String status, String fromDate, String toDate, int page, int size) {
        List<Booking> allBookings = bookingRepository.findByUserUserId(userId);
        List<Booking> filteredBookings = new ArrayList<>();

        for(Booking booking : allBookings) {
            if((status == null || booking.getBookingStatus().equals(status)) &&
               (fromDate == null || booking.getStartTime().toString().compareTo(fromDate) >= 0) &&
               (toDate == null || booking.getEndTime().toString().compareTo(toDate) <= 0)) {
                filteredBookings.add(booking);
            }
        }

        int totalCount = filteredBookings.size();
        int start = page * size;
        int end = Math.min(start + size, totalCount);
        List<Booking> paginatedBookings = filteredBookings.subList(start, end);

        Map<String, Object> response = new HashMap<>();
        response.put("bookings", paginatedBookings);
        response.put("totalCount", totalCount);
        response.put("page", page);
        response.put("size", size);
        double totalPages = Math.ceil((double) totalCount / size);
        response.put("totalPages", (int) totalPages);
        return response;
    }

    //Bổ sung sau
    private boolean hasTimeConflict(Booking booking) {
        if (booking == null || booking.getVehicle() == null ||
                booking.getStartTime() == null || booking.getEndTime() == null) {
            return false;
        }

        List<Booking> existingBookings = bookingRepository.findByVehicleVehicleId(booking.getVehicle().getVehicleId());

        for (Booking existing : existingBookings) {
            if (existing != null &&
                    !"Cancelled".equals(existing.getBookingStatus()) &&
                    !"Completed".equals(existing.getBookingStatus())&&
                    existing.getStartTime() != null &&
                    existing.getEndTime() != null) {

                if (booking.getStartTime().before(existing.getEndTime()) &&
                        booking.getEndTime().after(existing.getStartTime())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Booking checkIn(Integer bookingId, User user, User staff) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.setBookingStatus("Confirmed");

        booking.setStaff(staff);
        // Create contract, report, etc.
        userService.logAudit(user, "Checked in booking " + bookingId);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking returnVehicle(Integer bookingId, User user, User staff) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.setBookingStatus("Completed");
        Vehicle vehicle = booking.getVehicle();
        vehicle.setStatus("Available");
        vehicleRepository.save(vehicle);
        userService.logAudit(user, "Returned vehicle for booking " + bookingId);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getUserHistory(Integer userId) {
        return bookingRepository.findByUserUserId(userId);
    }

    @Override
    public Map<String, Object> getUserAnalytics(Integer userId) {
        List<Booking> bookings = getUserHistory(userId);
        BigDecimal totalCost = bookings.stream().map(Booking::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        long count = bookings.size();
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalBookings", count);
        stats.put("totalCost", totalCost);
        return stats;
    }

    @Override
    public Booking modifyBooking(Integer bookingId, Booking updates, User actor) {
        return null;
    }

    @Override
    public Booking cancelBooking(Integer bookingId, User actor) {
        return null;
    }

    @Override
    public Map<String, Object> settleBooking(Integer bookingId, User actor) {
        return Map.of();
    }
}
