package com.luanvan.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luanvan.model.Material;
import com.luanvan.service.MaterialService;

@RestController
@RequestMapping("materials")
public class MaterialController {

	private MaterialService materialService;
	
	
	@Autowired
	public MaterialController (MaterialService materialService) {
		this.materialService = materialService;
	}
	
	@GetMapping
	public List<Material> getMaterialAll(){
		return materialService.findAllMaterial();
	}
	
	@GetMapping("/{id}")
	public Optional<Material> findByMaterialId(@PathVariable Long id) {
		return materialService.findMaterialById(id);
	}
	
	@PostMapping()
	public void create(@RequestBody Material material) {
		materialService.save(material);
		
	}
	
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Long id) {
		materialService.delete(id);
		
	}
}
