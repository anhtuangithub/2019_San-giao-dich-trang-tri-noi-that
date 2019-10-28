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
public class ReviewAccountDTO {
	private Long id;
	private String content;
	private Date created_at;
	private int rating;
	private Users user;
	private Product products;
	
	@Getter @Setter
	public static class Users{
		private String email;
		private Customer customer;
		private Store store;
	}
	
	@Getter @Setter
	public static class Customer{
		private String name;
	}
	@Getter @Setter
	public static class Store{
		private String id;
		private String name;
	}
	
	@Getter @Setter
	public static class Product{
		private Long id;
		private String name;
		private String avatar;
		private Store stores;
	}
}
