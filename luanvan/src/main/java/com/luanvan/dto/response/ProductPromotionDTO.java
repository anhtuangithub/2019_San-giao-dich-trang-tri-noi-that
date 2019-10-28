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
public class ProductPromotionDTO {
	
	@Getter @Setter private Long id;
	
	@Getter @Setter	private String name;
	@Getter @Setter private String avatar;
	@Getter @Setter private String plug;
	@Getter @Setter private float avgstart;
	@Getter @Setter private int status;
	@Setter private List<UnitPrice> unitPrices;
	@Getter @Setter private int quantity;
	@Getter @Setter private int buyQuantity;
	@Getter @Setter private Date created_at;
	@Setter private List<OrderDetail> orderDetail;
	
	@Setter
	private List<Promotion> promotions;
	
	@Getter @Setter private Store stores;
	
	
	@Getter @Setter
	public static class Store{
		private Long id;
		private String name;
		private Province province;
	}
	
	@Getter @Setter
	public static class Province{
		private String name;
	}
	
	@Getter @Setter
	public static class UnitPrice{
		private Long id;
		private int price;
		private Date start_time;
		private Date end_time;
		private int root;
	}
	
	@Getter @Setter
	public static class Promotion{
		private Long id;
		private String name;
		private int sale_off;
		private Date start_time;
		private Date end_time;
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
