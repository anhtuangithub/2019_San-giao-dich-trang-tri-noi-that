package com.luanvan.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.luanvan.dto.request.PromotionProductDTO;
import com.luanvan.model.Promotion;

public interface PromotionService {

	// list Promotion	
	List<Promotion> findAllPromotion();
	
	// Save and update Promotion
	void save(Promotion promotion, Authentication auth);
	
	//Delete Promotion
	void delete(Long id);
	
	// Get Promotion by id
	PromotionProductDTO findPromotionById(Long id);
	
	//Save Promotion for Product
	void savePromotionForProduct(PromotionProductDTO promotionProductDTO, Authentication auth);
	
	//List product of promotion
	
//	List<PromotionProductDTO> findAllProductOfPromotion();
	
	List<Promotion> findByStore(Authentication auth);
}
