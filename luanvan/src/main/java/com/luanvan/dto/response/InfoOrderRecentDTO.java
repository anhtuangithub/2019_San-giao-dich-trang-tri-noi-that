package com.luanvan.dto.response;

import java.util.Date;
import java.util.List;

import com.luanvan.model.Color;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class InfoOrderRecentDTO {
	
	@Getter @Setter private Long id;
	@Getter @Setter private String address;
	@Getter @Setter private Date created_at;
	@Getter @Setter private String phone;
	
	
	@Getter @Setter private OrderStatus orderStatus;
	@Getter @Setter private PaymentType paymentType;
	@Getter @Setter private Store stores;
	
	@Getter @Setter private List<OrderDetail> orderDetail;
	
	@Getter @Setter
	public static class OrderStatus{
		private Long id;
		private String name;
	}
	
	@Getter @Setter
	public static class PaymentType{
		private String name;
	}
	@Getter @Setter
	public static class Store{
		private Long id;
		private String name;
	}
	@Getter @Setter
	public static class OrderDetail{
		private int quantity;
		private Product product;
		private Color colors;
		private Promotion promotion;
		private UnitPrice unitPrice;
		
		public int getTotalPrice() {
	    	int price = 0;
	    	int priceminus = unitPrice.getPrice()/1000;
	        if(promotion != null) {
	        	price = (priceminus*(100-promotion.getSale_off())/100)*getQuantity()*1000;
	        }       	
	        else price = unitPrice.getPrice() * getQuantity();
	        return price;
	    }

	}
	@Getter @Setter
	public static class Product{
		private Long id;
		private String name;
		private String avatar;
	}
	
	@Getter @Setter
	public static class Promotion{
		private int sale_off;
	}
	@Getter @Setter
	public static class UnitPrice{
		private int price;
	}
	
	public int getTotal() {
		int total = 0;
       for(OrderDetail orderDetail: orderDetail) {
    	   total += orderDetail.getTotalPrice();
       }
       return total;
    }
	
}
