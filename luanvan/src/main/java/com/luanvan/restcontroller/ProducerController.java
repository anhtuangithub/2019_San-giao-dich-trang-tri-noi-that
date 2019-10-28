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

import com.luanvan.model.Producer;
import com.luanvan.service.ProducerService;

@RestController
@RequestMapping("producers")
public class ProducerController {

	private ProducerService producerService;
	
	@Autowired
	public ProducerController (ProducerService producerService) {
		this.producerService = producerService;
	}
	
	@GetMapping
	public List<Producer> findAllProducer(){
		return producerService.findAllProducer();
	}
	
	@GetMapping("/{id}")
	public Optional<Producer> findProducerById(@PathVariable Long id) {
		return producerService.findProducerById(id);
	}
	
	@PostMapping()
	public void create(@RequestBody Producer producer) {
		producerService.save(producer);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		producerService.delete(id);
	}
}
