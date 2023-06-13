package com.github.tablesheep233.tx.sample.order.service;

import com.github.tablesheep233.tx.sample.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


}
