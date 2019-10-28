package com.luanvan.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luanvan.dto.response.ReviewAccountDTO;
import com.luanvan.dto.response.ReviewDTO;
import com.luanvan.model.Review;
import com.luanvan.service.ReviewService;

@RestController
@RequestMapping("reviews")
public class ReviewController {

	private ReviewService reviewService;
	
	@Autowired
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	@GetMapping
	public List<Review> findAllReview(){
		return reviewService.findAllReview();
	}
	
	@GetMapping("/{id}")
	public Optional<Review> findReviewById(@PathVariable Long id){
		return reviewService.findById(id);
	}
	
	@GetMapping("product-detail/{id}")
	public List<ReviewDTO> reviewOfProduct(@PathVariable Long id){
		return reviewService.reviewOfProduct(id);
	}
	
	@GetMapping("/check-review-of-customer/{productid}/{orderid}")
	public ReviewDTO checkReview(@PathVariable Long productid,@PathVariable Long orderid ) {
		return reviewService.checkReview(productid, orderid);
	}
	
	@GetMapping("tong-review-store")
	public float sumReviewStore(Authentication auth) {
		return reviewService.sumReviewStore(auth);
	}
	
	@GetMapping("tong-review")
	public float sumReview() {
		return reviewService.sumReview();
	}
	
	@GetMapping("review-of-store")
	public List<Review> ReviewOfStore(Authentication auth) {
		return reviewService.reviewOfStore(auth);
	}
	
	@GetMapping("review-of-user")
	public Page<ReviewAccountDTO> ReviewOfUser(Authentication auth,@RequestParam(value ="page", required = false, defaultValue = "0") int page) {
		return reviewService.reviewOfUser(auth,page);
	}
	
	@PostMapping
	public void save(@RequestBody Review review, Authentication auth) {
		reviewService.save(review,auth);
	}
	
	@DeleteMapping
	public void delete(@PathVariable Long id) {
		reviewService.delete(id);
	}
	
}
