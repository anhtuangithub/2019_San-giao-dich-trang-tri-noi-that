package com.luanvan.dto.response;

import java.util.List;

import com.luanvan.model.Customer;
import com.luanvan.model.Role;
import com.luanvan.model.Store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	
	private String email;
	
	 private Store store;
	 
	 private Customer customer;
	 
	 private List<Role> roles;
}
