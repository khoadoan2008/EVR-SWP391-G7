package com.khoadoan.basic.demoswp.repository;

import com.khoadoan.basic.demoswp.entity.RiskFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskFlagRepository extends JpaRepository<RiskFlag, Integer> {
}
