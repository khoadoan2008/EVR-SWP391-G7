package com.khoadoan.basic.demoswp.repository;

import com.khoadoan.basic.demoswp.entity.StaffSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffScheduleRepository extends JpaRepository<StaffSchedule, Integer> {
}
