package com.luanvan.service;

import java.util.List;
import java.util.Optional;

import com.luanvan.model.Material;

public interface MaterialService {

	
	// list material	
	List<Material> findAllMaterial();
	
	// Save and update material
	void save(Material material);
	
	//Delete material
	void delete(Long id);
	
	// Get Material by id
	Optional<Material> findMaterialById(Long id);
	
}
