package com.luanvan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luanvan.model.Producer;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

}
