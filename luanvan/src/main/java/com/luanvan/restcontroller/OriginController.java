package com.luanvan.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luanvan.model.Origin;
import com.luanvan.service.OriginService;

@RestController
@RequestMapping("origins")

public class OriginController {

	private OriginService originService;
	
	@Autowired
	public OriginController(OriginService originService) {
		this.originService = originService;
	}
	
	@GetMapping
	public  List<Origin> findOriginAll() {
		return originService.findAllOrigin();
	}
	
	@PostMapping()
	public void create(@RequestBody Origin origin){
		originService.save(origin);
	}
	
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Long id) {
		originService.delete(id);
	}
}
