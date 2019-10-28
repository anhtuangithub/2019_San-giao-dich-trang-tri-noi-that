package com.luanvan.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luanvan.model.OrderStatus;
import com.luanvan.service.OrderStatusService;

@RestController
@RequestMapping("orderstatus")
public class OrderStatusController {

	private OrderStatusService orderStatusService;
	
	@Autowired
	public OrderStatusController(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}
	
	@GetMapping
	public List<OrderStatus> getOrderStatusAll(){
		return orderStatusService.findAllOrderStatus();
	}
	
	@GetMapping("/{id}")
	public Optional<OrderStatus> findByOrderStatusId(@PathVariable Long id) {
		return orderStatusService.findOrderStatusById(id);
	}
	
	@PostMapping()
	public void create(@RequestBody OrderStatus orderStatus) {
		orderStatusService.save(orderStatus);
		
	}
	
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Long id) {
		orderStatusService.delete(id);;
		
	}
}
