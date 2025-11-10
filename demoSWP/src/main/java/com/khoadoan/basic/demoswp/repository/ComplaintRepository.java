package com.khoadoan.basic.demoswp.repository;

import com.khoadoan.basic.demoswp.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint,Integer> {
    List<Complaint> findByStatus(String status);
}
