package com.luanvan.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luanvan.model.Answer;
import com.luanvan.repo.AnswerRepository;
import com.luanvan.service.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService{

	private AnswerRepository answerRepository;
	
	@Autowired
	public AnswerServiceImpl(AnswerRepository answerRepository) {
		this.answerRepository = answerRepository;
	}

	@Override
	public List<Answer> findAllAnswer() {
		return answerRepository.findAll();
	}

	@Override
	public void save(Answer answer) {
		answerRepository.save(answer);
		
	}

	@Override
	public void delete(Long id) {
		answerRepository.deleteById(id);
		
	}

	@Override
	public Optional<Answer> findById(Long id) {
		return answerRepository.findById(id);
	}

	@Override
	public List<Answer> findAnswerByQuestion(Long questionId) {
		return answerRepository.findByQuestionsId(questionId);
	}

	@Override
	public void update(Long id, String content) {
		Answer answer = answerRepository.getOne(id);
		answer.setContent(content);
		answerRepository.save(answer);
	}
	
	
}
