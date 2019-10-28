package com.luanvan.restcontroller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luanvan.dto.response.CustomerResponseDTO;
import com.luanvan.model.Customer;
import com.luanvan.service.CustomerService;

@RestController
@RequestMapping("customers")
public class CustomerController {

	private CustomerService customerService;
	public CustomerController (CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping
	public List<Customer> findAllCustomer(){
		return customerService.findAllCustomer();
	}
	
	@GetMapping("all-customer-dto")
	public List<CustomerResponseDTO> findAllCustomerDTO(){
		return customerService.findCustomerDTOResponse();
	}
	
	
	@PostMapping
	public void create(@RequestBody Customer customer) {
		customerService.save(customer);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		customerService.delete(id);
	}
	
	@GetMapping("/{id}")
	public Customer findCustomerById(@PathVariable Long id) {
		return customerService.findCustomerById(id);
	}
	
	
	@GetMapping("thong-tin")
	public Customer findCustomerAuth(Authentication auth) {
		return customerService.findAuthen(auth);
	}
}
