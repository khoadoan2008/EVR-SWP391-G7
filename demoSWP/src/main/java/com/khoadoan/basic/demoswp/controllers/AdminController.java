package com.khoadoan.basic.demoswp.controllers;

import com.khoadoan.basic.demoswp.entity.*;
import com.khoadoan.basic.demoswp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    // Fleet monitoring
    @GetMapping("/fleet/summary")
    public ResponseEntity<Map<String, Object>> getFleetSummary(@RequestParam(required = false) Integer stationId) {
        if (stationId == null) {
            // Return overall fleet summary
            return ResponseEntity.ok(adminService.getFleetSummary(1)); // Mock for overall
        }
        return ResponseEntity.ok(adminService.getFleetSummary(stationId));
    }

    @PostMapping("/fleet/dispatch")
    public ResponseEntity<Vehicle> dispatchVehicle(
            @RequestParam Integer fromStationId,
            @RequestParam Integer toStationId,
            @RequestParam Integer vehicleId) {
        return ResponseEntity.ok(adminService.dispatchVehicle(fromStationId, toStationId, vehicleId));
    }

    // Customer management
    @GetMapping("/customers")
    public ResponseEntity<List<User>> getCustomers() {
        return ResponseEntity.ok(adminService.getCustomersWithRiskFlags());
    }

    @PostMapping("/customers/{id}/flag")
    public ResponseEntity<RiskFlag> flagCustomer(
            @PathVariable Integer id,
            @RequestParam Integer adminId,
            @RequestParam String reason,
            @RequestParam Integer riskScore) {
        return ResponseEntity.ok(adminService.flagCustomer(id, adminId, reason, riskScore));
    }

    @GetMapping("/complaints")
    public ResponseEntity<List<Complaint>> getComplaints(@RequestParam(required = false) String status) {
        if (status == null) {
            return ResponseEntity.ok(adminService.getComplaintsByStatus("Pending"));
        }
        return ResponseEntity.ok(adminService.getComplaintsByStatus(status));
    }

    // Staff management
    @GetMapping("/staff")
    public ResponseEntity<List<User>> getStaff(@RequestParam(required = false) Integer stationId) {
        if (stationId == null) {
            return ResponseEntity.ok(adminService.getStaffByStation(1)); // Mock for all
        }
        return ResponseEntity.ok(adminService.getStaffByStation(stationId));
    }

    @GetMapping("/staff/{id}/performance")
    public ResponseEntity<Map<String, Object>> getStaffPerformance(@PathVariable Integer id) {
        return ResponseEntity.ok(adminService.getStaffPerformance(id));
    }

    @PostMapping("/staff/schedule")
    public ResponseEntity<StaffSchedule> createStaffSchedule(
            @RequestParam Integer staffId,
            @RequestParam Integer stationId,
            @RequestParam String shiftStart,
            @RequestParam String shiftEnd,
            @RequestParam String shiftType) {
        LocalDateTime start = LocalDateTime.parse(shiftStart);
        LocalDateTime end = LocalDateTime.parse(shiftEnd);
        return ResponseEntity.ok(adminService.createStaffSchedule(staffId, stationId, start, end, shiftType));
    }

    // Reports and analytics
    @GetMapping("/reports/revenue")
    public ResponseEntity<Map<String, Object>> getRevenueReport(
            @RequestParam(required = false) Integer stationId,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {
        LocalDateTime fromDate = from != null ? LocalDateTime.parse(from) : LocalDateTime.now().minusMonths(1);
        LocalDateTime toDate = to != null ? LocalDateTime.parse(to) : LocalDateTime.now();

        if (stationId == null) {
            stationId = 1; // Default station
        }
        return ResponseEntity.ok(adminService.getRevenueReport(stationId, fromDate, toDate));
    }

    @GetMapping("/reports/utilization")
    public ResponseEntity<Map<String, Object>> getUtilizationReport(@RequestParam(required = false) Integer stationId) {
        if (stationId == null) {
            stationId = 1; // Default station
        }
        return ResponseEntity.ok(adminService.getUtilizationReport(stationId));
    }

    @GetMapping("/reports/peaks")
    public ResponseEntity<Map<String, Object>> getPeakHoursAnalysis(@RequestParam(required = false) Integer stationId) {
        if (stationId == null) {
            stationId = 1; // Default station
        }
        return ResponseEntity.ok(adminService.getPeakHoursAnalysis(stationId));
    }

    // AI demand forecasting (placeholder)
    @GetMapping("/reports/forecast")
    public ResponseEntity<Map<String, Object>> getDemandForecast(@RequestParam(required = false) Integer stationId) {
        Map<String, Object> forecast = new java.util.HashMap<>();
        forecast.put("predictedDemand", 85); // Mock AI prediction
        forecast.put("confidence", 0.78);
        forecast.put("recommendedActions", List.of("Increase fleet by 2 vehicles", "Schedule additional staff"));
        return ResponseEntity.ok(forecast);
    }
}
