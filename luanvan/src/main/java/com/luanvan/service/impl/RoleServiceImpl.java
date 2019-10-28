package com.luanvan.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luanvan.model.Role;
import com.luanvan.repo.RoleRepository;
import com.luanvan.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	private RoleRepository roleRepository;
	
	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public List<Role> findAllRole() {
		return roleRepository.findAll();
	}

	@Override
	public void save(Role role) {
		roleRepository.save(role);
		
	}

	@Override
	public void delete(Long id) {
		roleRepository.deleteById(id);
		
	}

	@Override
	public Optional<Role> findRoleById(Long id) {
		return roleRepository.findById(id);
	}
	
}
