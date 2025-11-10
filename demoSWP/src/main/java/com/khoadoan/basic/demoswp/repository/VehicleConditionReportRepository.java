package com.khoadoan.basic.demoswp.repository;

import com.khoadoan.basic.demoswp.entity.VehicleConditionReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleConditionReportRepository extends JpaRepository<VehicleConditionReport,Integer> {
}
