package com.luanvan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.luanvan.dto.request.RoleUserDTO;
import com.luanvan.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

	
	@Query(value = "Select users.id as id, count(user_role.role_id) as count From users join user_role on user_role.user_id = users.id group by users.id", nativeQuery=true)
	List<RoleUserDTO> countUserAllRole();
	
	Users findByEmail(String email);
}
