package com.luanvan.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.luanvan.dto.response.StoreDTOResponse;
import com.luanvan.exception.NotFoundException;
import com.luanvan.model.Store;
import com.luanvan.model.Users;
import com.luanvan.repo.StoreRepository;
import com.luanvan.repo.UsersRepository;
import com.luanvan.service.StoreService;

@Service
public class StoreServiceImpl implements StoreService {

	private StoreRepository storeRepository;
	private UsersRepository userRepository;
	
	@Autowired
	public StoreServiceImpl(StoreRepository storeRepository,
			UsersRepository userRepository) {
		this.storeRepository = storeRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<Store> findAllStore() {
		// TODO Auto-generated method stub
		return storeRepository.findAll();
	}

	@Override
	public void save(Store store, Authentication auth) {
		Users userid = userRepository.findByEmail(auth.getName());
		store.setUsers(userid);
		storeRepository.save(store);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		storeRepository.deleteById(id);
	}

	@Override
	public Store findStoreById(Long id) {
		// TODO Auto-generated method stub
		return storeRepository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Override
	public List<StoreDTOResponse> findStoreDTOResponse() {
		List<Store> stores = storeRepository.findAll();
		ModelMapper mapper = new ModelMapper();
		List<StoreDTOResponse> storeDTO = mapper.map(stores,new TypeToken<List<StoreDTOResponse>>(){}.getType());
		return storeDTO;
	}

	@Override
	public StoreDTOResponse storeLogIn(Authentication auth) {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Store store = storeRepository.findByUsersId(userid);
		ModelMapper mapper = new ModelMapper();
		StoreDTOResponse storeDTO = mapper.map(store,StoreDTOResponse.class);
		return storeDTO;
	}


	
	
}
