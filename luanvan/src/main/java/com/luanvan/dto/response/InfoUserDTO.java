package com.luanvan.dto.response;

import com.luanvan.model.Customer;
import com.luanvan.model.Store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoUserDTO {
	
	private String email;
	private Store store;
	private Customer customer;

}
