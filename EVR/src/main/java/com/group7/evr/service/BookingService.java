package com.group7.evr.service;

import com.group7.evr.entity.Booking;
import com.group7.evr.entity.User;
import com.group7.evr.entity.Vehicle;
import com.group7.evr.enums.BookingStatus;
import com.group7.evr.enums.VehicleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group7.evr.repository.BookingRepository;
import com.group7.evr.repository.VehicleRepository;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserService userService;

    public Booking createBooking(Booking booking, User user) {
        Vehicle vehicle = vehicleRepository.findById(booking.getVehicle().getVehicleId()).orElseThrow();
        
        // Enhanced validation
        if (!VehicleStatus.AVAILABLE.equals(vehicle.getStatus())) {
            throw new RuntimeException("Vehicle not available");
        }
        
        // Check for time conflicts
        if (hasTimeConflict(booking)) {
            throw new RuntimeException("Booking time conflicts with existing booking");
        }
        
        // Validate booking dates
        if (booking.getStartTime() != null && booking.getEndTime() != null) {
            if (booking.getStartTime().after(booking.getEndTime())) {
                throw new RuntimeException("Start time cannot be after end time");
            }
        }
        
        vehicle.setStatus(VehicleStatus.RENTED);
        vehicleRepository.save(vehicle);
        booking.setUser(user);
        booking.setBookingStatus(BookingStatus.PENDING);
        userService.logAudit(user, "Created booking " + booking.getBookingId());
        return bookingRepository.save(booking);
    }

    // --- New: Get booking by ID ---
    public Booking getBookingById(Integer bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    // --- New: Enhanced user bookings with filters ---
    public Map<String, Object> getUserBookingsWithFilters(Integer userId, String status, String fromDate, String toDate, int page, int size) {
        List<Booking> allBookings = bookingRepository.findByUserUserId(userId);
        
        // Apply filters
        List<Booking> filteredBookings = allBookings.stream()
                .filter(booking -> status == null || booking.getBookingStatus().toString().equals(status))
                .filter(booking -> fromDate == null || booking.getStartTime().toString().compareTo(fromDate) >= 0)
                .filter(booking -> toDate == null || booking.getStartTime().toString().compareTo(toDate) <= 0)
                .toList();
        
        // Apply pagination
        int start = page * size;
        int end = Math.min(start + size, filteredBookings.size());
        List<Booking> paginatedBookings = filteredBookings.subList(start, end);
        
        Map<String, Object> response = new HashMap<>();
        response.put("bookings", paginatedBookings);
        response.put("totalCount", filteredBookings.size());
        response.put("page", page);
        response.put("size", size);
        response.put("totalPages", (int) Math.ceil((double) filteredBookings.size() / size));
        
        return response;
    }

    // --- Helper: Check for time conflicts ---
    private boolean hasTimeConflict(Booking newBooking) {
        List<Booking> existingBookings = bookingRepository.findByVehicleVehicleId(newBooking.getVehicle().getVehicleId());
        
        return existingBookings.stream()
                .filter(booking -> !BookingStatus.CANCELLED.equals(booking.getBookingStatus()) && !BookingStatus.COMPLETED.equals(booking.getBookingStatus()))
                .anyMatch(booking -> {
                    // Simple time overlap check
                    return (newBooking.getStartTime().before(booking.getEndTime()) && 
                           newBooking.getEndTime().after(booking.getStartTime()));
                });
    }

    public Booking checkIn(Integer bookingId, User user, User staff) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setStaff(staff);
        // Create contract, report, etc.
        userService.logAudit(user, "Checked in booking " + bookingId);
        return bookingRepository.save(booking);
    }

    public Booking returnVehicle(Integer bookingId, User user, User staff) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.setBookingStatus(BookingStatus.COMPLETED);
        Vehicle vehicle = booking.getVehicle();
        vehicle.setStatus(VehicleStatus.AVAILABLE);
        vehicleRepository.save(vehicle);
        userService.logAudit(user, "Returned vehicle for booking " + bookingId);
        return bookingRepository.save(booking);
    }

    public List<Booking> getUserHistory(Integer userId) {
        return bookingRepository.findByUserUserId(userId);
    }

    public Map<String, Object> getUserAnalytics(Integer userId) {
        List<Booking> bookings = getUserHistory(userId);
        BigDecimal totalCost = bookings.stream().map(Booking::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        long count = bookings.size();
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalBookings", count);
        stats.put("totalCost", totalCost);
        return stats;
    }

    // --- New: Advanced Analytics ---
    public Map<String, Object> getAdvancedUserAnalytics(Integer userId) {
        List<Booking> bookings = getUserHistory(userId);
        
        // Peak/Off-peak analysis
        Map<String, Long> peakHours = bookings.stream()
                .collect(Collectors.groupingBy(
                    booking -> {
                        LocalTime startTime = booking.getStartTime().toLocalDate().atStartOfDay().toLocalTime();
                        int hour = startTime.getHour();
                        return (hour >= 7 && hour <= 9) || (hour >= 17 && hour <= 19) ? "Peak" : "Off-Peak";
                    },
                    Collectors.counting()
                ));
        
        // Distance analysis (mock - would need actual distance calculation)
        BigDecimal totalDistance = bookings.stream()
                .map(booking -> booking.getVehicle().getMileage() != null ? booking.getVehicle().getMileage() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Spending categories
        Map<String, BigDecimal> spendingCategories = new HashMap<>();
        spendingCategories.put("rental", bookings.stream()
                .map(Booking::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        spendingCategories.put("fees", BigDecimal.ZERO); // Would calculate from actual fees
        
        Map<String, Object> analytics = new HashMap<>();
        analytics.put("peakHours", peakHours);
        analytics.put("totalDistance", totalDistance);
        analytics.put("spendingCategories", spendingCategories);
        analytics.put("totalBookings", bookings.size());
        
        return analytics;
    }

    // --- New: Modify booking ---
    public Booking modifyBooking(Integer bookingId, Booking updates, User actor) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (BookingStatus.COMPLETED.equals(booking.getBookingStatus()) || BookingStatus.CANCELLED.equals(booking.getBookingStatus())) {
            throw new RuntimeException("Booking cannot be modified in current status");
        }
        if (updates.getStartTime() != null) booking.setStartTime(updates.getStartTime());
        if (updates.getEndTime() != null) booking.setEndTime(updates.getEndTime());
        if (updates.getVehicle() != null && updates.getVehicle().getVehicleId() != null
        && !updates.getVehicle().getVehicleId().equals(booking.getVehicle().getVehicleId())) {
            Vehicle current = booking.getVehicle();
            current.setStatus(VehicleStatus.AVAILABLE);
            vehicleRepository.save(current);
            Vehicle next = vehicleRepository.findById(updates.getVehicle().getVehicleId()).orElseThrow();
            if (!VehicleStatus.AVAILABLE.equals(next.getStatus())) {
                throw new RuntimeException("New vehicle is not available");
            }
            next.setStatus(VehicleStatus.RENTED);
            vehicleRepository.save(next);
            booking.setVehicle(next);
        }
        userService.logAudit(actor, "Modified booking " + bookingId);
        return bookingRepository.save(booking);
    }

    // --- New: Cancel booking ---
    public Booking cancelBooking(Integer bookingId, User actor) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (BookingStatus.COMPLETED.equals(booking.getBookingStatus()) || BookingStatus.CANCELLED.equals(booking.getBookingStatus())) {
            return booking; // idempotent
        }
        booking.setBookingStatus(BookingStatus.CANCELLED);
        Vehicle vehicle = booking.getVehicle();
        vehicle.setStatus(VehicleStatus.AVAILABLE);
        vehicleRepository.save(vehicle);
        userService.logAudit(actor, "Cancelled booking " + bookingId);
        return bookingRepository.save(booking);
    }

    // --- New: Enhanced Settlement ---
    public Map<String, Object> settleBooking(Integer bookingId, User actor) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        
        BigDecimal basePrice = booking.getTotalPrice() != null ? booking.getTotalPrice() : BigDecimal.ZERO;
        BigDecimal lateFee = calculateLateFee(booking);
        BigDecimal damageFee = calculateDamageFee(booking);
        BigDecimal energyFee = calculateEnergyFee(booking);
        
        BigDecimal extraFees = lateFee.add(damageFee).add(energyFee);
        BigDecimal total = basePrice.add(extraFees);
        
        Map<String, Object> settlement = new HashMap<>();
        settlement.put("basePrice", basePrice);
        settlement.put("lateFee", lateFee);
        settlement.put("damageFee", damageFee);
        settlement.put("energyFee", energyFee);
        settlement.put("extraFees", extraFees);
        settlement.put("total", total);
        
        userService.logAudit(actor, "Settled booking " + bookingId + " with total: " + total);
        return settlement;
    }
    
    private BigDecimal calculateLateFee(Booking booking) {
        // Mock calculation - would use actual time differences
        return BigDecimal.ZERO;
    }
    
    private BigDecimal calculateDamageFee(Booking booking) {
        // Mock calculation - would check vehicle condition reports
        return BigDecimal.ZERO;
    }
    
    private BigDecimal calculateEnergyFee(Booking booking) {
        // Mock calculation - would check battery usage
        return BigDecimal.ZERO;
    }
}
