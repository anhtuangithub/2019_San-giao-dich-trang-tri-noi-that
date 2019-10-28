package com.luanvan.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.luanvan.model.Member;

public interface MemberService {

	//List Member
	List<Member> findAllMember();
	
	// Save and update Member
	void save(Member member);
	
	// Delete Member
	void delete(Long id);
	
	// Find Member by ID
	Member findMemberById(Long id);
	
	List<Member> HistoryMember(Authentication auth);
	
	void giaHan(Authentication auth, int memberTypeId);
}
