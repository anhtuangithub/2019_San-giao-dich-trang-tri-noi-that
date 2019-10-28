package com.luanvan.dto.request;

import java.util.Set;

import com.luanvan.model.Color;
import com.luanvan.model.Product;

public class ProductDTO  {

	private Product product;
	private Set<Color> colors;
	
	
	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}

	public Set<Color> getColors() {
		return colors;
	}


	public void setColors(Set<Color> colors) {
		this.colors = colors;
	}

	public ProductDTO() {
		super();
	}
	
	public ProductDTO(Product product, Set<Color> colors) {
		super();
		this.product = product;
		this.colors = colors;
	}
	
	


	
}
