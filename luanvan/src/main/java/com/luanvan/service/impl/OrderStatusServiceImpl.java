package com.luanvan.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luanvan.model.OrderStatus;
import com.luanvan.repo.OrderStatusRepository;
import com.luanvan.service.OrderStatusService;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

	private OrderStatusRepository orderStatusRepository;

	@Autowired
	public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository) {
		this.orderStatusRepository = orderStatusRepository;
	}
	
	@Override
	public List<OrderStatus> findAllOrderStatus() {
		return orderStatusRepository.findAll();
	}

	@Override
	public void save(OrderStatus orderStatus) {
		orderStatusRepository.save(orderStatus);
		
	}

	@Override
	public void delete(Long id) {
		orderStatusRepository.deleteById(id);
		
	}

	@Override
	public Optional<OrderStatus> findOrderStatusById(Long id) {
		return orderStatusRepository.findById(id);
	}
}
