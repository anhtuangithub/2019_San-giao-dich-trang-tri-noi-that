package com.luanvan.service;

import java.util.List;
import java.util.Optional;

import com.luanvan.model.Role;

public interface RoleService {

	// list Role	
	List<Role> findAllRole();
	
	// Save and update role
	void save(Role role);
	
	//Delete role
	void delete(Long id);
	
	// Get Role by id
	Optional<Role> findRoleById(Long id);
}
