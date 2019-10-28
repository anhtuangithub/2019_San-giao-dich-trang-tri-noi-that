package com.luanvan.dto.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luanvan.model.OrderDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAdminDTO {
	
	private Long id;
	private String address;
	private String phone;
	private PaymentType paymentType;
	private Date created_at;
	private Store stores;
	private Users user;
	@JsonIgnore
	private List<OrderDetail> orderDetail;
	private OrderStatus orderStatus;
	
	
	@Getter @Setter
	public static class PaymentType{
		private String name;
	}
	
	@Getter @Setter
	public static class OrderStatus{
		private Long id;
		private String name;
	}
	
	
	@Getter @Setter
	public static class Store{
		private Long id;
		private String name;
		private String phone;
	}
	
	@Getter @Setter
	public static class Users{
		private String email;
		private Store store;
		private Customer customer;
	}
	
	@Getter @Setter
	public static class Customer{
		private Long id;
		private String name;
		private String phone;
	}
	
	public int getTotal() {
		int total = 0;
       for(OrderDetail orderDetail: this.getOrderDetail()) {
    	   total += orderDetail.getTotalPrice();
       }
       return total;
    }
}
