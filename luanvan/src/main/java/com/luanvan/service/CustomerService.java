package com.luanvan.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.luanvan.dto.response.CustomerResponseDTO;
import com.luanvan.model.Customer;

public interface CustomerService {

	// list Customer	
	List<Customer> findAllCustomer();
	
	// Save and update Customer
	void save(Customer Customer);
	
	//Delete Customer
	void delete(Long id);
	
	// Get Customer by id
	Customer findCustomerById(Long id);
	
	List<CustomerResponseDTO> findCustomerDTOResponse();
	
	Customer findAuthen(Authentication auth);
}
