package com.luanvan.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.luanvan.dto.request.CartDTO;
import com.luanvan.dto.response.StoreDTOResponse;
import com.luanvan.exception.NotFoundException;
import com.luanvan.model.Store;
import com.luanvan.model.Users;
import com.luanvan.repo.StoreRepository;
import com.luanvan.repo.UsersRepository;
import com.luanvan.service.SendGridMailService;
import com.luanvan.service.StoreService;

@Service
public class StoreServiceImpl implements StoreService {

	private StoreRepository storeRepository;
	private UsersRepository userRepository;
	private SendGridMailService sendGridMailService;
	
	@Autowired
	public StoreServiceImpl(StoreRepository storeRepository,
			UsersRepository userRepository,
			SendGridMailService sendGridMailService) {
		this.storeRepository = storeRepository;
		this.userRepository = userRepository;
		this.sendGridMailService = sendGridMailService;
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

	@Override
	public void xetDuyet(CartDTO cartDTO) {
		Store store = storeRepository.getOne(cartDTO.getId());
		store.setStatus(cartDTO.getQuantity());
		storeRepository.save(store);
		
		StringBuilder  string = new StringBuilder("NoiThat246 Xin chào bạn !!!");
		if(cartDTO.getQuantity() == 1) {
			string.append("<p>Yêu câu bán hàng của bạn đã được duyệt.</p>");
		}
		else if(cartDTO.getQuantity() == 2) {
			string.append("<p>Yêu câu bán hàng của bạn đã bị từ chối do bạn không đáp ứng yêu cầu của Nội Thất 246.</p>");
		}
		else if(cartDTO.getQuantity() == 3) {
			string.append("<p>Tài khoản của bạn tạm khóa do vi phạm điều lệ của NoiThat246.</p>");
		}
		string.append("<p>Mọi thắc mắc và góp ý vui lòng liên hệ với Nội Thất Care qua email: support@noithat246.vn hoặc số điện thoại 0941 426 824 (1000đ/phút , 8-21h kể cả T7, CN).</p>");
		string.append("<p>Trân trọng</p><p>NoiThat246</p>");
		sendGridMailService.sendHTML(store.getUsers().getEmail(), "Xác nhận bán hàng cùng NoiThat246", string.toString());
	}


	
	
}
