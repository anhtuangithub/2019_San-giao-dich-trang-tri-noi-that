package com.luanvan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luanvan.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

	List<Answer> findByQuestionsId(Long questionId);
}
