package com.luanvan.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luanvan.model.Color;
import com.luanvan.repo.ColorRepository;
import com.luanvan.service.ColorService;

@Service
public class ColorServiceImpl  implements ColorService{

	
	private ColorRepository colorRepository;
	
	@Autowired
	public ColorServiceImpl(ColorRepository colorRepository) {
		this.colorRepository = colorRepository;
	}
	@Override
	public List<Color> findAllColor() {
		return colorRepository.findAll();
	}

	@Override
	public void save(Color color) {
		colorRepository.save(color);
		
	}

	@Override
	public void delete(Long id) {
		colorRepository.deleteById(id);
		
	}

	@Override
	public Optional<Color> findById(Long id) {
		return colorRepository.findById(id);
	}
	

}
