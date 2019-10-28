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

import com.luanvan.model.Color;
import com.luanvan.service.ColorService;

@RestController
@RequestMapping("colors")
public class ColorController {

	private ColorService colorService;
	
	@Autowired
	public ColorController(ColorService colorService) {
		this.colorService = colorService;
	}
	
	@GetMapping
	public List<Color> findAllColor(){
		return colorService.findAllColor();
	}
	@GetMapping("/{id}")
	public Optional<Color> findByColorId(@PathVariable Long id) {
		return colorService.findById(id);
	}
	
	@PostMapping
	public void create(@RequestBody Color color) {
		colorService.save(color);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		colorService.delete(id);
	}
	
}
