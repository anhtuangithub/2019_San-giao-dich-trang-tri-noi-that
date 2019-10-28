package com.luanvan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luanvan.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	List<Member> findByStoresIdOrderByIdDesc(Long storeid);
	
	Member findFirstByStoresIdOrderByIdDesc(Long storeid);
}
