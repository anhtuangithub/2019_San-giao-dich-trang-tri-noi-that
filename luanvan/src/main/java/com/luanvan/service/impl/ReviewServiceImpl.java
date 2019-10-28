package com.luanvan.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.luanvan.dto.response.ReviewAccountDTO;
import com.luanvan.dto.response.ReviewDTO;
import com.luanvan.exception.NotFoundException;
import com.luanvan.model.Product;
import com.luanvan.model.Review;
import com.luanvan.model.Users;
import com.luanvan.repo.ProductRepository;
import com.luanvan.repo.ReviewRepository;
import com.luanvan.repo.StoreRepository;
import com.luanvan.repo.UsersRepository;
import com.luanvan.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService{

	private ReviewRepository reviewRepository;
	private UsersRepository userRepository;
	private ProductRepository productRepository;
	private StoreRepository storeRepository;
	
	@Autowired
	public ReviewServiceImpl(ReviewRepository reviewRepository,
			UsersRepository userRepository,
			ProductRepository productRepository,
			StoreRepository storeRepository) {
		this.reviewRepository = reviewRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.storeRepository = storeRepository;
	}

	@Override
	public List<Review> findAllReview() {
		return reviewRepository.findAll();
	}

	@Override
	@Transactional
	public void save(Review review ,Authentication auth) {
		Users userid = userRepository.findByEmail(auth.getName());
		review.setUser(userid);
		reviewRepository.save(review);
		
		float avgstart = reviewRepository.avg(review.getProducts().getId());
		Long productid = review.getProducts().getId();
		Product product = productRepository.getOne(productid);
		product.setAvgstart(avgstart);
		productRepository.save(product);
	}

	@Override
	public void delete(Long id) {
		reviewRepository.deleteById(id);
		
	}

	@Override
	public Optional<Review> findById(Long id) {
		return reviewRepository.findById(id);
	}

	@Override
	public List<ReviewDTO> reviewOfProduct(Long id) {
		List<Review> reviews = reviewRepository.findByProductsIdAndStatusOrderByIdDesc(id, 0);
		ModelMapper mapper = new ModelMapper();
		List<ReviewDTO> reviewDTOs = mapper.map(reviews,new TypeToken<List<ReviewDTO>>(){}.getType());
		return reviewDTOs.stream().limit(5).collect(Collectors.toList());
	}

	@Override
	public ReviewDTO checkReview(Long productid, Long orderid) {
		Review review = reviewRepository.findByProductsIdAndOrderId(productid, orderid).orElseThrow(NotFoundException::new);
		ModelMapper mapper = new ModelMapper();
		ReviewDTO reviewDTOs = mapper.map(review,ReviewDTO.class);
		return reviewDTOs;
	}

	@Override
	public float sumReviewStore(Authentication auth) {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Long storeid = storeRepository.findByUsersId(userid).getId();
		return reviewRepository.sumReviewStore(storeid);
	}

	@Override
	public float sumReview() {
		return reviewRepository.sumReview();
	}

	@Override
	public List<Review> reviewOfStore(Authentication auth) {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Long storeid = storeRepository.findByUsersId(userid).getId();
		return reviewRepository.listReviewStore(storeid);
	}

	@Override
	public Page<ReviewAccountDTO> reviewOfUser(Authentication auth, int page) {
		int pageminus = 0;
		if(page>=1) {
			pageminus = page-1;
		}
		Pageable sorted =  PageRequest.of(pageminus, 1);
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Page<Review> reviews = reviewRepository.findByUserIdOrderByIdDesc(userid,sorted);
		Page<ReviewAccountDTO> reviewDTOs = reviews.map(new Function<Review, ReviewAccountDTO>() {
		    @Override
		    public ReviewAccountDTO apply(Review entity) {
		    	ModelMapper mapper = new ModelMapper();
		    	ReviewAccountDTO reviewDTO = mapper.map(entity,ReviewAccountDTO.class);
		        return reviewDTO;
		    }
		});
		
		return reviewDTOs;
	}
	
	
}
