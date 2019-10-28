package com.luanvan.dto.request;

public class CartDTO {
	private Long id;
	private int quantity;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public CartDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartDTO(Long id, int quantity) {
		super();
		this.id = id;
		this.quantity = quantity;
	}
	
	
	
}
