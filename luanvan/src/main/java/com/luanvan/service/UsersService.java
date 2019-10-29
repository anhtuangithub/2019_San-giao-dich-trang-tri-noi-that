package com.luanvan.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;

import com.luanvan.dto.request.CustomerDTO;
import com.luanvan.dto.request.ResetPasswordDTO;
import com.luanvan.dto.request.RoleUserDTO;
import com.luanvan.dto.request.StoreDTO;
import com.luanvan.dto.request.TestDTO;
import com.luanvan.dto.response.InfoUserDTO;
import com.luanvan.dto.response.UserDTO;
import com.luanvan.model.Users;



public interface UsersService {

	// list User	
	List<Users> findAllUsers();
	
	// Save and update User
	void save(Users users);
	
	//Delete Users
	void delete(Long id);
	
	// Get User by id
	Optional<Users> findUsersById(Long id);
	
	//
	List<RoleUserDTO> countUserAllRole();
	
	void multiSave(TestDTO testDTO);
	
	UserDTO findByEmail(String email);
	
	Map<String, String> checkEmail(String email);
	
	void saveCustomer(CustomerDTO customerDTO);
	
	void saveStore(StoreDTO storeDTO, Long id);
	
	InfoUserDTO info(Authentication auth);
	
	void resetPassword(String email);
	
	void savePassword(ResetPasswordDTO passwordDTO);
}
