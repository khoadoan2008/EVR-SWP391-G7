package com.khoadoan.basic.demoswp.repository;

import com.khoadoan.basic.demoswp.entity.IssueReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueReportRepository extends JpaRepository<IssueReport, Integer> {
    List<IssueReport> findByVehicleVehicleId(Integer vehicleId);
    List<IssueReport> findByStationStationId(Integer stationId);
    List<IssueReport> findByStatus(String status);
    List<IssueReport> findByPriority(String priority);
    List<IssueReport> findByIssueCategory(String category);
}
