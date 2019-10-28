package com.luanvan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luanvan.model.MemberType;

public interface MemberTypeRepository extends JpaRepository<MemberType, Long> {

}
