package com.luanvan.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.luanvan.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

	@Email(message = "email không hợp lệ")
	@NotBlank(message = "Vui lòng nhập email")
	private String email;
	
	@NotBlank(message = "Vui lòng nhập password")
	@Length(min = 8, max = 32, message = "Độ dài phải từ 8 -32 kí tự")
	private String password;
	
	@Valid
	private Customer customers;
}
