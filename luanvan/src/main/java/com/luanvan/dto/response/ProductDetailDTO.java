package com.luanvan.dto.response;

import java.util.Date;
import java.util.List;

import com.luanvan.model.OrderDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTO {

	
	@Getter @Setter private Long id;
	
	@Getter @Setter	private String name;
	@Getter @Setter private String avatar;
	@Getter @Setter private int quantity;
	@Getter @Setter private float avgstart;
	@Getter @Setter private String description;
	
	@Setter private List<UnitPrice> unitPrices;
	@Setter private List<Promotion> promotions;
	@Getter @Setter private Producer producers;
	@Getter @Setter private Origin origins;
	@Getter @Setter private Material materials;
	@Getter @Setter private List<Color> colors;
	@Getter @Setter private Store stores;
	@Getter @Setter private Category categorys;
	
	@Setter private List<OrderDetail> orderDetail;
	
	@Getter @Setter
	public static class UnitPrice{
		private Long id;
		private int price;
		private Date start_time;
		private Date end_time;
		private int root;
	}
	@Getter @Setter
	public static class Producer{
		private Long id;
		private String name;
	}
	@Getter @Setter
	public static class Category{
		private Long id;
		private String name;
	}
	
	@Getter @Setter
	public static class Promotion{
		private Long id;
		private String name;
		private int sale_off;
		private Date start_time;
		private Date end_time;
	}
	
	@Getter @Setter
	public static class Origin{
		private Long id;
		private String name;
	}
	@Getter	@Setter
	public static class Material{
		private Long id;
		private String name;
	}
	@Getter @Setter
	public static class Color{
		private Long id;
		private String name;
	}
	
	@Getter @Setter
	public static class Store{
		private Long id;
		private String name;
	}
	
	public Promotion getMaxPromotion() {
		int max = 0;
		Promotion promotionActive = new Promotion();
		Date today = new Date();
			for(Promotion promotion:promotions) {
				if(promotion.getStart_time().before(today)
					&& promotion.getEnd_time().after(today)
					&& max < promotion.getSale_off()) 
				{
					promotionActive = promotion;
					max = promotion.getSale_off();
				}
						
			}
			
	return promotionActive;
	}
	public int getPrice_new() {
		Long max = (long)0;
		int value = getPrice();
		Date today = new Date();
		
		for(UnitPrice unitPrice:unitPrices) {
			if(unitPrice.getRoot() != 1) {
				if(unitPrice.getStart_time().before(today)
					&& unitPrice.getEnd_time().after(today)
					&& max < unitPrice.getId()) 
				{
					max = unitPrice.getId();
					value = unitPrice.getPrice();
				}		
			}
		}
			int sale_off = getMaxPromotion().getSale_off();
			int pricetemp = value/1000;
		return  ((pricetemp*100-pricetemp*sale_off)/100)*1000;
	}
	
	public int getPrice() {
		int price = 0;
		Long max = (long)0;
		for(UnitPrice unitPrice:unitPrices) {
			if(unitPrice.getRoot() == 1 && max < unitPrice.getId()) 
			{
				max = unitPrice.getId();
				price = unitPrice.getPrice();
			}		
		}
		return  price;
	}
	
	public int getInventory_number() {
		int total = 0;
		for(OrderDetail detail:orderDetail) {
			if(detail.getOrders().getOrderStatus().getId() < 6)
			total += detail.getQuantity();
		}
		return this.quantity-total;
	}
}
