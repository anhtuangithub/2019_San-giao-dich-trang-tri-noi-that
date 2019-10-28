package com.luanvan.restcontroller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luanvan.model.MemberType;
import com.luanvan.service.MemberTypeService;

@RestController
@RequestMapping("member-types")
public class MemberTypeController {

	
	private MemberTypeService memberTypeService;
	public MemberTypeController(MemberTypeService memberTypeService) {
		this.memberTypeService = memberTypeService;
	}
	
	@GetMapping
	public List<MemberType> findAllMemberType(){
		return memberTypeService.findAllMemberType();
	}
	
	@PostMapping
	public void create(@RequestBody MemberType memberType) {
		memberTypeService.save(memberType);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		memberTypeService.delete(id);
	}
	
	@GetMapping("/{id}")
	public MemberType findByMemberTypeId(@PathVariable Long id) {
		return memberTypeService.findMemberTypeById(id);
	}
}
