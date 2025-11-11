package com.khoadoan.basic.demoswp.repository;

import com.khoadoan.basic.demoswp.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station,Integer> {
}
