package com.luanvan.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.luanvan.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	List<Member> findByStoresIdOrderByIdDesc(Long storeid);
	
	Member findFirstByStoresIdOrderByIdDesc(Long storeid);
	
	@Query(value = "select * from members  where members.store_id = :store and members.end_time > CURRENT_DATE  order by members.id desc limit 1", nativeQuery=true)
	Optional<Member> memberStore(@Param("store")Long store);
}
