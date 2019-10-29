package com.luanvan.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {
	
	@Email(message = "email không hợp lệ")
	@NotBlank(message = "Vui lòng nhập email")
	private String email;
	
	@NotBlank(message = "Vui lòng nhập password")
	@Length(min = 8, max = 32, message = "Mật khẩu phải có độ dài phải từ 8 -32 kí tự")
	private String password;
}
