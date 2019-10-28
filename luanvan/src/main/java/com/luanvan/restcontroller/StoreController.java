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

import com.luanvan.dto.response.StoreDTOResponse;
import com.luanvan.model.Store;
import com.luanvan.service.StoreService;

@RestController
@RequestMapping("stores")
public class StoreController {

	private StoreService storeService;
	
	@Autowired
	public StoreController(StoreService storeService) {
		this.storeService = storeService;
	}
	
	@GetMapping
	public List<Store> findAllStore(){
		return storeService.findAllStore();
	}
	
	@GetMapping("all-store-dto")
	public List<StoreDTOResponse> findAllStoreDTO(){
		return storeService.findStoreDTOResponse();
	}
	
	@PostMapping
	public void create(@RequestBody Store store,Authentication auth) {
		storeService.save(store,auth);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		storeService.delete(id);
	}
	
	@GetMapping("/{id}")
	public Store findStoreById(@PathVariable Long id){
		return storeService.findStoreById(id);
	}
	
	@GetMapping("/thong-tin")
	public StoreDTOResponse getStore(Authentication auth){
		return storeService.storeLogIn(auth);
	}
}
