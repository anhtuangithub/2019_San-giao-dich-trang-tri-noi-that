package com.luanvan.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.luanvan.dto.request.CartDTO;
import com.luanvan.dto.response.StoreDTOResponse;
import com.luanvan.model.Store;

public interface StoreService {

	//List Store
	List<Store> findAllStore();
	// save and update store
	void save(Store store, Authentication auth);
	//Delete Store
	void delete(Long id);
	// Find by Id
	Store findStoreById(Long id);
	
	List<StoreDTOResponse> findStoreDTOResponse();
	
	StoreDTOResponse storeLogIn(Authentication auth);
	
	void xetDuyet(CartDTO cartDTO);
	
}
