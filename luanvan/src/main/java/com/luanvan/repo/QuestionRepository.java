package com.luanvan.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.luanvan.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	List<Question> findByProductsIdAndStatus(Long id, int status);
	
	@Query(value = "select * from question join product on product.id = question.product_id where product.store_id = :store order by question.id desc",nativeQuery=true)
	List<Question> listQuestionStore(@Param("store") Long store);
	
	Page<Question> findByUserIdOrderByIdDesc(Long userid, Pageable pageable);
	
	Page<Question> findByProductsIdAndStatusOrderByIdDesc(Long productid, int status, Pageable pageable);
}
