package com.luanvan.service.impl;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luanvan.model.Producer;
import com.luanvan.repo.ProducerRepository;
import com.luanvan.service.ProducerService;

@Service
public class ProducerServiceImpl implements ProducerService {

	private ProducerRepository producerRepository;
	
	@Autowired
	public ProducerServiceImpl(ProducerRepository producerRepository) {
		this.producerRepository = producerRepository;
	}
	
	
	@Override
	public List<Producer> findAllProducer(){
		return producerRepository.findAll();	
	}


	@Override
	public void save(Producer producer) {
		producer.setPlug(covertToString(producer.getName()));
		producerRepository.save(producer);
		
	}


	@Override
	public void delete(Long id) {
		producerRepository.deleteById(id);
		
	}


	@Override
	public Optional<Producer> findProducerById(Long id) {
		return producerRepository.findById(id);
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
