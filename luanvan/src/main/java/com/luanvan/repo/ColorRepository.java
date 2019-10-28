package com.luanvan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luanvan.model.Color;

public interface ColorRepository extends JpaRepository<Color, Long> {

}
