package com.luanvan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.luanvan.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

	
	@Query(value = "SELECT * FROM image   where product_id = :product", nativeQuery=true)
	List<Image> imageOfPro(@Param("product") Long product);
}
