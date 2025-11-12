package com.khoadoan.basic.demoswp.repository;

import com.khoadoan.basic.demoswp.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {

    List<Maintenance> findByStatus(String status);

    List<Maintenance> findByStationStationId(Integer stationId);
}
