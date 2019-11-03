package com.luanvan.dto.response;

import com.luanvan.model.District;
import com.luanvan.model.Province;
import com.luanvan.model.Ward;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StoreDTOResponse {
	
	private Long id;
	private String name;
	private String address;
	private String phone;
	private String website;
	private String introduce;
	private int status;
	private Users users;
	private Province province;
	private District district;
	private Ward ward;
	
	@Getter @Setter
	public static class Users{
		private Long id;
		private String email;
	}
}
