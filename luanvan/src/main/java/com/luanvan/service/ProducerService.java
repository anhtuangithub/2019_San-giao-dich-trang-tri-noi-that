package com.luanvan.service;

import java.util.List;
import java.util.Optional;

import com.luanvan.model.Producer;

public interface ProducerService {
	
	
	// list Producer	
	List<Producer> findAllProducer();
	
	// Save and update Producer
	void save(Producer Producer);
	
	//Delete Producer
	void delete(Long id);
	
	// Get Producer by id
	Optional<Producer> findProducerById(Long id);
}
