package com.luanvan.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luanvan.dto.request.PromotionProductDTO;
import com.luanvan.model.Promotion;
import com.luanvan.service.PromotionService;

@RestController
@RequestMapping("promotions")
public class PromotionController {

	private PromotionService promotionService;
	
	@Autowired
	public PromotionController(PromotionService promotionService) {
		this.promotionService = promotionService;
	}
	
	@GetMapping()
	public List<Promotion> findAllPromotion(){
		return promotionService.findAllPromotion();
	}
	
	@GetMapping("khuyen-mai-store")
	public List<Promotion> findAllPromotionByStore(Authentication auth){
		return promotionService.findByStore(auth);
	}
	
	@GetMapping("/{id}")
	public PromotionProductDTO findPromotionById(@PathVariable Long id) {
		return promotionService.findPromotionById(id);	
	}
	
	@PostMapping
	public void create(@RequestBody Promotion promotion, Authentication auth) {
		promotionService.save(promotion, auth);
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		promotionService.delete(id);
	}
	
	
	@PostMapping("/promotion-products")
	public void save(@RequestBody PromotionProductDTO promotionProductDTO, Authentication auth) {
		promotionService.savePromotionForProduct(promotionProductDTO, auth);
	}
	
}
