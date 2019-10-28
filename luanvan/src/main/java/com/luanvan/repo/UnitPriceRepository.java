package com.luanvan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luanvan.model.UnitPrice;

public interface UnitPriceRepository extends JpaRepository<UnitPrice, Long>{
	
	List<UnitPrice> findByProductIdOrderByRootAsc(Long id);
	
	List<UnitPrice> findByProductIdAndRoot(Long id,int value);

}
