package com.luanvan.dto.request;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionProductDTO {
	private Long id ;
	private String name;
	private String description;
	private int sale_off;
	private int status;
	private Date create_at;
	private Date start_time;
	private Date end_time;
	private List<Product> products;
	
	@Getter
	@Setter
	public static class Product{
		private Long id;
		private String name;
	}
	
	
	
	
	
	
	
}
