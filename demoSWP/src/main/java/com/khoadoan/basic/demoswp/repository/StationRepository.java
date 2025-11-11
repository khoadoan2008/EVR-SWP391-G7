package com.khoadoan.basic.demoswp.repository;

import com.khoadoan.basic.demoswp.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface StationRepository extends JpaRepository<Station,Integer> {
    List<Station> findByLatitudeBetweenAndLongitudeBetween(Double minLat, Double maxLat, Double minLng, Double maxLng);
}
