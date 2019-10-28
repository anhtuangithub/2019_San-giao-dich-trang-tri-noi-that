package com.luanvan.dto.response;

import java.util.Date;
import java.util.List;

import com.luanvan.model.Color;

import lombok.Getter;
import lombok.Setter;

public class OrderGroupResponseDTO {
	
	@Getter @Setter private Long id;
	@Getter @Setter private String address;
	@Getter @Setter private Date created_at;
	@Getter @Setter private String phone;
	
	@Getter @Setter private PaymentType paymentType;
	
	@Getter @Setter private List<Order> orders;
	
	
	@Getter @Setter
	public static class Order{
		private Long id;
		private String address;
		private List<OrderDetail> orderDetail;
		private PaymentType paymentType;
		private OrderStatus orderStatus;
		private Store stores;
		private Users user;
		
		public int getTotal() {
			int total = 0;
	       for(OrderDetail orderDetail: getOrderDetail()) {
	    	   total += orderDetail.getTotalPrice();
	       }
	       return total;
	    }
	}
	
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
	public static class Users{
		private Long id;
		private String email;
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
       for(Order order: orders) {
    	   total += order.getTotal();
       }
       return total;
    }
}
