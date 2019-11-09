package com.luanvan.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.luanvan.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	@Query(value = "SELECT avg(rating) FROM review   where product_id = ?1", nativeQuery=true)
	float avg(Long id);
	
	List<Review> findByProductsIdAndStatusOrderByIdDesc(Long id,int status);
	
	Optional<Review> findByProductsIdAndOrderId(Long productId, Long orderId);
	
//	List<Review> findByProductsStoresId();
	
	@Query(value = "select count(review.id) from review " + 
			"join product on review.product_id = product.id " + 
			"where product.store_id = :store", nativeQuery=true)
	float sumReviewStore(@Param("store") Long store );
	
	@Query(value = "select count(review.id) from review " + 
			" join product on review.product_id = product.id ", nativeQuery=true)
	float sumReview();
	
	@Query(value = "select * from review join product on product.id = review.product_id where product.store_id = :store ORDER by review.id desc",nativeQuery=true)
	List<Review> listReviewStore(@Param("store") Long store);
	
	Page<Review> findByUserIdOrderByIdDesc(Long userid,Pageable pageable);
	
	Page<Review> findByProductsIdAndStatusOrderByIdDesc(Long productid, int status, Pageable pageable);
}
