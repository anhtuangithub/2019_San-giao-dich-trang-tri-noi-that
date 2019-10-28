package com.luanvan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luanvan.model.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

	List<Promotion> findByStoreId(Long storeid);
}
