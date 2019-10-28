package com.luanvan.service;

import java.util.List;
import java.util.Optional;

import com.luanvan.model.Color;

public interface ColorService {

	//List color
	List<Color> findAllColor();
	
	// Save and update Color
	void save(Color color);
	
	// Delete color
	void delete(Long id);
	
	// Find Color by ID
	
	Optional<Color> findById(Long id);
}
