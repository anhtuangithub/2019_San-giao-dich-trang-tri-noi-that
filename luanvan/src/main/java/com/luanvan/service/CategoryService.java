package com.luanvan.service;

import java.util.List;
import java.util.Optional;

import com.luanvan.model.Category;
public interface CategoryService {
	
	List<Category> findAllCategory();
	
	List<Category> findByName();
	
	Optional<Category> findById(Long id);
	
	List<Category> findByParentId(Long id);
	
	Category save(Category category);
	
	void delete(Long id);
	
	List<Category> listCategoryChild();
}
