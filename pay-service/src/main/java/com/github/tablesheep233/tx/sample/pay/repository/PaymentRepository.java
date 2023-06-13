package com.github.tablesheep233.tx.sample.pay.repository;

import com.github.tablesheep233.tx.sample.pay.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
