package com.luanvan.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.luanvan.model.OrderGroup;

public interface OrderGroupRepository extends JpaRepository<OrderGroup, Long> {
	
	List<OrderGroup> findByUserId(Long id);
	List<OrderGroup> findByUserIdOrderByIdDesc(Long id);
	Page<OrderGroup> findByUserIdOrderByIdDesc(Long id,Pageable pageable);
}
