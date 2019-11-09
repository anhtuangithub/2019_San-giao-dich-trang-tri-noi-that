package com.luanvan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import com.luanvan.dto.response.ReviewAccountDTO;
import com.luanvan.dto.response.ReviewDTO;
import com.luanvan.model.Review;

public interface ReviewService {

	//List Review
	List<Review> findAllReview();
	
	// Save and update Review
	void save(Review review,Authentication auth);
	
	// Delete Review
	void delete(Long id);
	
	// Find Review by ID
	
	Optional<Review> findById(Long id);
	
	List<ReviewDTO> reviewOfProduct(Long id);
	
	ReviewDTO checkReview(Long productid, Long orderid);
	
	float sumReviewStore(Authentication auth);
	float sumReview();
	
	List<Review> reviewOfStore(Authentication auth);
	
	Page<ReviewAccountDTO> reviewOfUser(Authentication auth,int page);
	
	Page<ReviewDTO> reviewPageHome(Long productid, int page);
}
