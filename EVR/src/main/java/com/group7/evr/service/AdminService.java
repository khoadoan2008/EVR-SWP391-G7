package com.group7.evr.service;

import com.group7.evr.entity.*;
import com.group7.evr.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private RiskFlagRepository riskFlagRepository;
    @Autowired
    private StaffScheduleRepository staffScheduleRepository;
    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private UserService userService;

    // Fleet monitoring by station
    public Map<String, Object> getFleetSummary(Integer stationId) {
        List<Vehicle> vehicles = vehicleRepository.findByStationStationIdAndStatus(stationId, "Available");
        List<Vehicle> rentedVehicles = vehicleRepository.findByStationStationIdAndStatus(stationId, "Rented");
        List<Vehicle> maintenanceVehicles = vehicleRepository.findByStationStationIdAndStatus(stationId, "Maintenance");
        
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalVehicles", vehicles.size() + rentedVehicles.size() + maintenanceVehicles.size());
        summary.put("availableVehicles", vehicles.size());
        summary.put("rentedVehicles", rentedVehicles.size());
        summary.put("maintenanceVehicles", maintenanceVehicles.size());
        summary.put("occupancyRate", calculateOccupancyRate(vehicles.size(), rentedVehicles.size()));
        
        return summary;
    }

    // Vehicle dispatching
    public Vehicle dispatchVehicle(Integer fromStationId, Integer toStationId, Integer vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();
        Station targetStation = stationRepository.findById(toStationId).orElseThrow();
        
        if (!vehicle.getStation().getStationId().equals(fromStationId)) {
            throw new RuntimeException("Vehicle not at source station");
        }
        
        vehicle.setStation(targetStation);
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        userService.logAudit(null, "Dispatched vehicle " + vehicleId + " from station " + fromStationId + " to " + toStationId);
        return updatedVehicle;
    }

    // Customer management
    public List<User> getCustomersWithRiskFlags() {
        return userRepository.findAll().stream()
                .filter(user -> "Customer".equals(user.getRole()))
                .filter(user -> riskFlagRepository.findByUserUserId(user.getUserId()).size() > 0)
                .toList();
    }

    public RiskFlag flagCustomer(Integer customerId, Integer adminId, String reason, Integer riskScore) {
        User customer = userRepository.findById(customerId).orElseThrow();
        User admin = userRepository.findById(adminId).orElseThrow();
        
        RiskFlag riskFlag = new RiskFlag();
        riskFlag.setUser(customer);
        riskFlag.setFlaggedBy(admin);
        riskFlag.setReason(reason);
        riskFlag.setRiskScore(riskScore);
        
        RiskFlag savedFlag = riskFlagRepository.save(riskFlag);
        userService.logAudit(admin, "Flagged customer " + customerId + " with risk score " + riskScore);
        return savedFlag;
    }

    public List<Complaint> getComplaintsByStatus(String status) {
        return complaintRepository.findByStatus(status);
    }

    // Staff management
    public List<User> getStaffByStation(Integer stationId) {
        return userRepository.findAll().stream()
                .filter(user -> "Staff".equals(user.getRole()))
                .filter(user -> user.getStation() != null && user.getStation().getStationId().equals(stationId))
                .toList();
    }

    public Map<String, Object> getStaffPerformance(Integer staffId) {
        User staff = userRepository.findById(staffId).orElseThrow();
        List<Booking> handovers = bookingRepository.findByStaffUserId(staffId);
        
        Map<String, Object> performance = new HashMap<>();
        performance.put("totalHandovers", handovers.size());
        performance.put("station", staff.getStation().getName());
        performance.put("averageRating", calculateAverageRating(staffId)); // Mock calculation
        performance.put("lastActivity", getLastActivity(staffId));
        
        return performance;
    }

    public StaffSchedule createStaffSchedule(Integer staffId, Integer stationId, LocalDateTime shiftStart, 
                                           LocalDateTime shiftEnd, String shiftType) {
        User staff = userRepository.findById(staffId).orElseThrow();
        Station station = stationRepository.findById(stationId).orElseThrow();
        
        StaffSchedule schedule = new StaffSchedule();
        schedule.setStaff(staff);
        schedule.setStation(station);
        schedule.setShiftStart(shiftStart);
        schedule.setShiftEnd(shiftEnd);
        schedule.setShiftType(shiftType);
        
        StaffSchedule savedSchedule = staffScheduleRepository.save(schedule);
        userService.logAudit(null, "Created schedule for staff " + staffId);
        return savedSchedule;
    }

    // Reports and analytics
    public Map<String, Object> getRevenueReport(Integer stationId, LocalDateTime from, LocalDateTime to) {
        List<Booking> bookings = bookingRepository.findByStationStationId(stationId);
        BigDecimal totalRevenue = bookings.stream()
                .map(Booking::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        Map<String, Object> report = new HashMap<>();
        report.put("totalRevenue", totalRevenue);
        report.put("totalBookings", bookings.size());
        report.put("averageBookingValue", bookings.isEmpty() ? BigDecimal.ZERO : 
                totalRevenue.divide(BigDecimal.valueOf(bookings.size())));
        
        return report;
    }

    public Map<String, Object> getUtilizationReport(Integer stationId) {
        List<Vehicle> vehicles = vehicleRepository.findByStationStationIdAndStatus(stationId, "Available");
        List<Vehicle> rentedVehicles = vehicleRepository.findByStationStationIdAndStatus(stationId, "Rented");
        
        Map<String, Object> utilization = new HashMap<>();
        utilization.put("totalVehicles", vehicles.size() + rentedVehicles.size());
        utilization.put("utilizedVehicles", rentedVehicles.size());
        utilization.put("utilizationRate", calculateUtilizationRate(vehicles.size(), rentedVehicles.size()));
        
        return utilization;
    }

    public Map<String, Object> getPeakHoursAnalysis(Integer stationId) {
        List<Booking> bookings = bookingRepository.findByStationStationId(stationId);
        
        Map<String, Long> hourlyDistribution = bookings.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    booking -> String.valueOf(booking.getStartTime().getHours()),
                    java.util.stream.Collectors.counting()
                ));
        
        Map<String, Object> analysis = new HashMap<>();
        analysis.put("hourlyDistribution", hourlyDistribution);
        analysis.put("peakHour", findPeakHour(hourlyDistribution));
        analysis.put("totalBookings", bookings.size());
        
        return analysis;
    }

    // Helper methods
    private Double calculateOccupancyRate(int available, int rented) {
        int total = available + rented;
        return total == 0 ? 0.0 : (double) rented / total * 100;
    }

    private Double calculateUtilizationRate(int available, int rented) {
        int total = available + rented;
        return total == 0 ? 0.0 : (double) rented / total * 100;
    }

    private Double calculateAverageRating(Integer staffId) {
        // Mock calculation - would use actual rating system
        return 4.5;
    }

    private LocalDateTime getLastActivity(Integer staffId) {
        // Mock calculation - would get from audit logs
        return LocalDateTime.now().minusHours(2);
    }

    private String findPeakHour(Map<String, Long> hourlyDistribution) {
        return hourlyDistribution.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }
}
