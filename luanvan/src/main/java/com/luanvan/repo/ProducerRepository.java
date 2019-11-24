package com.luanvan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luanvan.model.Producer;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

	
	List<Producer> findByStatus(int status);
}
