package service;

import entity.Booking;
import entity.User;
import entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.BookingRepository;
import repository.UserRepository;
import repository.VehicleRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (!"Available".equals(vehicle.getStatus())) {
            throw new RuntimeException("Vehicle not available");
        }
        vehicle.setStatus("Rented");
        vehicleRepository.save(vehicle);
        booking.setUser(user);
        booking.setBookingStatus("Pending");
        userService.logAudit(user, "Created booking " + booking.getBookingId());
        return bookingRepository.save(booking);
    }
    public Booking checkIn(Integer bookingId, User user, User staff) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.setBookingStatus("Confirmed");
        booking.setStaff(staff);
        // Create contract, report, etc.
        userService.logAudit(user, "Checked in booking " + bookingId);
        return bookingRepository.save(booking);
    }

    public Booking returnVehicle(Integer bookingId, User user, User staff) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.setBookingStatus("Completed");
        Vehicle vehicle = booking.getVehicle();
        vehicle.setStatus("Available");
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
}
