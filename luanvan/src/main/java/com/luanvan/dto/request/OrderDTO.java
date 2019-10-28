package com.luanvan.dto.request;

import java.util.List;

import com.luanvan.model.PaymentType;
import com.luanvan.model.Store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
	@Setter @Getter private String address;
	@Setter @Getter private String phone;
	@Setter @Getter private PaymentType paymentType;
	@Setter @Getter private Store stores;
	@Setter @Getter private List<OrderDetailDTO> ordersDetailDTO;
	
	
}
