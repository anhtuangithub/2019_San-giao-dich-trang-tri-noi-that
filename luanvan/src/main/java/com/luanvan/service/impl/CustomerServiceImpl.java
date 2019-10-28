package com.luanvan.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.luanvan.dto.response.CustomerResponseDTO;
import com.luanvan.exception.NotFoundException;
import com.luanvan.model.Customer;
import com.luanvan.repo.CustomerRepository;
import com.luanvan.repo.UsersRepository;
import com.luanvan.service.CustomerService;

@Service
public class CustomerServiceImpl  implements CustomerService {

	private CustomerRepository customerRepository;
	private UsersRepository userRepository;
	
	public CustomerServiceImpl (CustomerRepository customerRepository,
			UsersRepository userRepository) {
		this.customerRepository = customerRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public List<Customer> findAllCustomer() {
		return customerRepository.findAll();
	}

	@Override
	public void save(Customer Customer) {
		customerRepository.save(Customer);
		
	}

	@Override
	public void delete(Long id) {
		customerRepository.deleteById(id);
		
	}

	@Override
	public Customer findCustomerById(Long id) {
		return customerRepository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Override
	public List<CustomerResponseDTO> findCustomerDTOResponse() {
		List<Customer> customers = customerRepository.findAll();
		ModelMapper mapper = new ModelMapper();
		List<CustomerResponseDTO> customerDTO = mapper.map(customers,new TypeToken<List<CustomerResponseDTO>>(){}.getType());
		return customerDTO;
	}

	@Override
	public Customer findAuthen(Authentication auth) {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		return customerRepository.findByUsersId(userid);
	}

}
