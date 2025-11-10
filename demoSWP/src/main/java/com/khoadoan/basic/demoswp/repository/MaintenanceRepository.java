package com.khoadoan.basic.demoswp.repository;

import com.khoadoan.basic.demoswp.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {

}
