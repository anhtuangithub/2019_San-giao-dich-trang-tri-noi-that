package com.luanvan.service;

import java.util.List;
import java.util.Optional;

import com.luanvan.model.Origin;

public interface OriginService {

	// list Origin	
	List<Origin> findAllOrigin();
	
	// Save and update origin
	void save(Origin origin);
	
	//Delete origin
	void delete(Long id);
	
	// Get Origin by id
	Optional<Origin> findOriginById(Long id);
}
