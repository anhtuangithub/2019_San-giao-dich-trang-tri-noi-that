package com.luanvan.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luanvan.model.Role;
import com.luanvan.service.RoleService;

@RestController
@RequestMapping("roles")

public class RoleController {

	private RoleService roleService;
	
	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@GetMapping()
	public List<Role> findAllRole(){
		return roleService.findAllRole();
	}
	
	@GetMapping("/{id}")
	public Optional<Role> findRoleById(@PathVariable Long id){
		return roleService.findRoleById(id);
	}
	
	@PostMapping()
	public void create(@RequestBody Role role) {
		roleService.save(role);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		roleService.delete(id);
	}
}
