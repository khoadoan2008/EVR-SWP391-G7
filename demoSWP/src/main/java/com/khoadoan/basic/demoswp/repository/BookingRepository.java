package com.khoadoan.basic.demoswp.repository;

import com.khoadoan.basic.demoswp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {
    List<Booking> findByUserUserId(Integer userId);
    List<Booking> findByStationStationIdAndBookingStatusIn(Integer stationId, List<String> statuses);
    List<Booking> findByStationStationId(Integer stationId);
    List<Booking> findByBookingStatus(String status);
    List<Booking> findByVehicleVehicleId(Integer vehicleId);
    List<Booking> findByStaffUserId(Integer staffId);
}
