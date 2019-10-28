package com.luanvan.service;

import java.util.List;
import java.util.Optional;

import com.luanvan.model.OrderStatus;

public interface OrderStatusService {
	// list OrderStatus	
	List<OrderStatus> findAllOrderStatus();
	
	// Save and update OrderStatus
	void save(OrderStatus orderStatus);
	
	//Delete OrderStatus
	void delete(Long id);
	
	// Get OrderStatus by id
	Optional<OrderStatus> findOrderStatusById(Long id);
}
