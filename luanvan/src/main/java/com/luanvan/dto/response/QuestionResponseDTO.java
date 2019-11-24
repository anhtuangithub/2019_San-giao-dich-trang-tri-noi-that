package com.luanvan.dto.response;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDTO {

	private Long id;
	private String content;
	private Date created_at;
	private List<Answer> answers;
	private Users user;
	private Product products;
	private int status;
	
	@Getter @Setter 
	public static class Users{
		private String email;
		private Store store;
		private Customer customer;
	}
	
	@Getter @Setter 
	public static class Product{
		private String name;
		private String avatar;
	}
	
	@Getter @Setter
	public static class Store{
		private String name;
	}
	
	@Getter @Setter
	public static class Customer{
		private String name;
	}
	@Getter @Setter
	public static class Answer{
		private Long id;
		private String content;
		private Date created_at;
		private Store stores;
	}
}
