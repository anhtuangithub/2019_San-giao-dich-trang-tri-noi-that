package com.luanvan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTOResponse {
	private Long id;
	private String path;
	private int status;
	private Product product_id;
	
	@Getter @Setter
	public static class Product{
		private int id;
	}
	
}
