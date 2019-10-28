package com.luanvan.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.luanvan.dto.request.PromotionProductDTO;
import com.luanvan.exception.NotFoundException;
import com.luanvan.model.Promotion;
import com.luanvan.model.Store;
import com.luanvan.model.Users;
import com.luanvan.repo.PromotionRepository;
import com.luanvan.repo.StoreRepository;
import com.luanvan.repo.UsersRepository;
import com.luanvan.service.PromotionService;

@Service
public class PromotionServiceImpl implements PromotionService {

	private PromotionRepository promotionRepository;
	private UsersRepository userRepository;
	private StoreRepository storeRepository;
	@Autowired
	public PromotionServiceImpl(
			PromotionRepository promotionRepository,
			UsersRepository userRepository,
			StoreRepository storeRepository) {
		this.promotionRepository = promotionRepository;
		this.userRepository = userRepository;
		this.storeRepository = storeRepository;
	}
	@Override
	public List<Promotion> findAllPromotion() {
		return promotionRepository.findAll();
	}
	@Override
	public void save(Promotion promotion,Authentication auth) {
		Users user = userRepository.findByEmail(auth.getName());
		Store store = storeRepository.findByUsersId(user.getId());
		promotion.setStore(store);
		promotionRepository.save(promotion);
		
	}
	@Override
	public void delete(Long id) {
		promotionRepository.deleteById(id);
		
	}
	@Override
	public PromotionProductDTO findPromotionById(Long id) {
		Promotion promotion = promotionRepository.findById(id).orElseThrow(NotFoundException::new);
		ModelMapper mapper = new ModelMapper();
		PromotionProductDTO mapDTO = mapper.map(promotion, PromotionProductDTO.class);
		return mapDTO;
	}
	@Override
	public void savePromotionForProduct(PromotionProductDTO promotionProductDTO, Authentication auth) {
		ModelMapper mapper = new ModelMapper();
		Promotion promotion = mapper.map(promotionProductDTO, Promotion.class);
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Store store = storeRepository.findByUsersId(userid);	
		promotion.setStore(store);
		promotionRepository.save(promotion);
	}
	@Override
	public List<Promotion> findByStore(Authentication auth) {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Long storeid = storeRepository.findByUsersId(userid).getId();
		return promotionRepository.findByStoreId(storeid);
	}

}
