package com.luanvan.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {

	private Long id;
	private String name;
	private String address;
	private String phone;
	private String sex;
	private Date birthday;
	private Users users;
	
	@Getter @Setter
	public static class Users{
		private Long id;
		private String email;
	}
}
