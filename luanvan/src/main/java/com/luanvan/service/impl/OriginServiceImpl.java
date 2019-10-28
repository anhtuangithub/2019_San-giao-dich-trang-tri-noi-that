package com.luanvan.service.impl;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luanvan.model.Origin;
import com.luanvan.repo.OriginRepository;
import com.luanvan.service.OriginService;

@Service
public class OriginServiceImpl implements OriginService {

	private OriginRepository originRepository;
	@Autowired
	public OriginServiceImpl (OriginRepository originRepository) {
		this.originRepository = originRepository;
	}
	
	@Override
	public List<Origin> findAllOrigin() {
		return originRepository.findAll();
	}

	@Override
	public void save(Origin origin) {
		origin.setPlug(covertToString(origin.getName()));
		originRepository.save(origin);
		
	}

	@Override
	public void delete(Long id) {
		originRepository.deleteById(id);
		
	}

	@Override
	public Optional<Origin> findOriginById(Long id) {
	return originRepository.findById(id);
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
