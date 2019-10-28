package com.luanvan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luanvan.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	Customer findByUsersId(Long id);

}
