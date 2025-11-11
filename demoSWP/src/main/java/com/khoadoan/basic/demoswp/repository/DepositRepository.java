package com.khoadoan.basic.demoswp.repository;

import com.khoadoan.basic.demoswp.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends JpaRepository<Deposit,Integer> {
}
