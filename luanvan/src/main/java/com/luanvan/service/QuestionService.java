package com.luanvan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import com.luanvan.dto.response.QuestionResponseDTO;
import com.luanvan.model.Question;

public interface QuestionService {

	//List Question
	List<Question> findAllQuestion();
	
	//List question by store
	List<Question> listQuestionByStore(Authentication auth);
	List<QuestionResponseDTO> listQuestionDTOByStore(Authentication auth);
	
	// Save and update Question
	void save(Question question,Authentication auth);
	
	// Delete Question
	void delete(Long id);
	
	//changeStatus
	void changeStatus(Long id);
	
	// Find Question by ID
	Optional<Question> findById(Long id);
	
	List<QuestionResponseDTO> findAllQuestionHome(Long productid,int status);
	
	Page<QuestionResponseDTO> questionByUser(Authentication auth, int page);
	
	Page<QuestionResponseDTO> AllQuestionHome(Long productid,int page);
	
	QuestionResponseDTO questionById(Long id);
}
