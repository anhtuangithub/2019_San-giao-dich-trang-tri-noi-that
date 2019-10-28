package com.luanvan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {

	@Getter @Setter private int quantity;
	@Getter @Setter private int total;
	
	@Getter @Setter private UnitPrice unitPrice;
	@Getter @Setter private Promotion promotion;
	@Getter @Setter private Product product;
	
	@Getter @Setter
	public static class UnitPrice{
		private int price;
	}
	
	@Getter @Setter
	public static class Promotion{
		private int sale_off;
	}
	
	@Getter @Setter
	public static class Product{
		private String name;
		private String avatar;
	}
}
