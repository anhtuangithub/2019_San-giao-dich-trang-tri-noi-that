package com.luanvan.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_detail")
public class OrderDetail {


	@EmbeddedId
	@JsonIgnore
    private OrderDetailId id;
    
    @Column
	private int quantity;
    
    @JsonIgnore
    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;
    
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("order_id")
    @JoinColumn(name = "order_id")
    private Order orders;
    
    @ManyToOne
    @JoinColumn(name="color_id")
    private Color colors;
    
    @ManyToOne
    @JoinColumn(name="price_id")
    private UnitPrice unitPrice;
    
    
    @ManyToOne
    @JoinColumn(name="promotion_id")
    private Promotion promotion;
    

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
