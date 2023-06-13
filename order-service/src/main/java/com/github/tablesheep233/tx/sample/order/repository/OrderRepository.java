package com.github.tablesheep233.tx.sample.order.repository;

import com.github.tablesheep233.tx.sample.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
