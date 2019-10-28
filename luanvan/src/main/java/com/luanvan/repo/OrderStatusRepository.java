package com.luanvan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luanvan.model.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

}
