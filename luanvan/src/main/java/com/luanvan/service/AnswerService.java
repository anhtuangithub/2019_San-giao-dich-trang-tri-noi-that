package com.luanvan.service;

import java.util.List;
import java.util.Optional;

import com.luanvan.model.Answer;

public interface AnswerService {

	//List Answer
	List<Answer> findAllAnswer();
	
	// Save and update Answer
	void save(Answer answer);
	
	// Delete Answer
	void delete(Long id);
	
	void update(Long id, String content);
	
	// Find Answer by ID
	Optional<Answer> findById(Long id);
	
	//Find Answer by Question
	List<Answer> findAnswerByQuestion(Long questionId);
}
