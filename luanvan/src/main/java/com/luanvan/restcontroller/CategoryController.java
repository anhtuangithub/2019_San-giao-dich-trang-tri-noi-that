package com.luanvan.restcontroller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luanvan.model.Category;
import com.luanvan.service.CategoryService;

@RestController
@RequestMapping("categories")
public class CategoryController {
	
	private CategoryService categoryService;
	
	@Autowired
	public CategoryController (CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public List<Category> findAllCategory(){
		return categoryService.findAllCategory();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category){
		return ResponseEntity.ok(categoryService.save(category));
	}
	
	
	@GetMapping("/{id}")
	public Optional<Category> findCategoryById(@PathVariable Long id){
		return categoryService.findById(id);
	}

	@GetMapping("/parentId/{id}")
	public List<Category> findCategoryByParentId(@PathVariable Long id){
		return categoryService.findByParentId(id);
	}
	
	@GetMapping("list-category-child")
	public List<Category> findCategoryChild(){
		return categoryService.listCategoryChild();
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		categoryService.delete(id);
	}
}