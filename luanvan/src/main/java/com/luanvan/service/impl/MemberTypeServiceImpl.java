package com.luanvan.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luanvan.exception.NotFoundException;
import com.luanvan.model.MemberType;
import com.luanvan.repo.MemberTypeRepository;
import com.luanvan.service.MemberTypeService;

@Service
public class MemberTypeServiceImpl  implements MemberTypeService{
	
	private MemberTypeRepository memberTypeRepository;
	public MemberTypeServiceImpl( MemberTypeRepository memberTypeRepository) {
		this.memberTypeRepository = memberTypeRepository;
	}
	@Override
	public List<MemberType> findAllMemberType() {
		return memberTypeRepository.findAll();
	}
	@Override
	public void save(MemberType MemberType) {
		memberTypeRepository.save(MemberType);
		
	}
	@Override
	public void delete(Long id) {
		memberTypeRepository.deleteById(id);
		
	}
	@Override
	public MemberType findMemberTypeById(Long id) {
		return memberTypeRepository.findById(id).orElseThrow(NotFoundException::new);
	}
	
	

}
