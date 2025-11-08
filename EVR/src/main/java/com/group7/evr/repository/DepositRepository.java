package com.group7.evr.repository;

import com.group7.evr.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends JpaRepository<Deposit,Integer> {
}
