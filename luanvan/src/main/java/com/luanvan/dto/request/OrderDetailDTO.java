package com.luanvan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
	
	@Getter @Setter private Product product;
	@Getter @Setter private Promotion promotion;
	@Getter @Setter private int quantity;
	
	@Getter
    @Setter
    public static class Product {
        private Long id;
    }
	
	@Getter
    @Setter
    public static class Promotion {
        private Long id;
    }
}
