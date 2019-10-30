package com.luanvan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luanvan.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	
	List<Inventory> findByProductsId(Long id);
}
