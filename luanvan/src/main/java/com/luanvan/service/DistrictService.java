package com.luanvan.service;

import java.util.List;
import java.util.Optional;

import com.luanvan.model.District;

public interface DistrictService {
	List<District> findAllDistrict();
	List<District> findDistrictByProvince(String id);
	Optional<District> getByProvince(String id);
}