package com.luanvan.service.impl;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luanvan.model.Material;
import com.luanvan.repo.MaterialRepository;
import com.luanvan.service.MaterialService;

@Service
public class MaterialServiceImpl implements MaterialService{

	private MaterialRepository  materialRepository;
	@Autowired
	public MaterialServiceImpl (MaterialRepository materialRepository) {
		this.materialRepository = materialRepository;
	}
	
	@Override
	public List<Material> findAllMaterial() {
		return materialRepository.findAll();
	}

	@Override
	public void save(Material material) {
		material.setPlug(covertToString(material.getName()));
		materialRepository.save(material);
		
	}

	@Override
	public void delete(Long id) {
		materialRepository.deleteById(id);
		
	}

	@Override
	public Optional<Material> findMaterialById(Long id) {
		return materialRepository.findById(id);
	}

	public static String covertToString(String value) {
	      try {
	            String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
	            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
	      } catch (Exception ex) {
	            ex.printStackTrace();
	      }
	      return null;
	}
}
