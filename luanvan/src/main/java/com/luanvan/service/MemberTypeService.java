package com.luanvan.service;

import java.util.List;

import com.luanvan.model.MemberType;

public interface MemberTypeService {

	// list MemberType	
	List<MemberType> findAllMemberType();
	
	// Save and update MemberType
	void save(MemberType MemberType);
	
	//Delete MemberType
	void delete(Long id);
	
	// Get MemberType by id
	MemberType findMemberTypeById(Long id);

}
