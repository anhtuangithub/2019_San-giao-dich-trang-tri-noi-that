package com.luanvan.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luanvan.model.Answer;
import com.luanvan.service.AnswerService;

@RestController
@RequestMapping("answers")
public class AnswerController {

	private AnswerService answerService;
	
	@Autowired
	public AnswerController(AnswerService answerService) {
		this.answerService = answerService;
	}
	
	@GetMapping
	public List<Answer> findAllAnswer(){
		return answerService.findAllAnswer();
	}
	
	@GetMapping("/{id}")
	public Optional<Answer> findAnswerById(@PathVariable Long id){
		return answerService.findById(id);
	}
	
	@GetMapping("/question/{id}")
	public List<Answer> findAnswerByIQuestion(@PathVariable Long id){
		return answerService.findAnswerByQuestion(id);
	}
	
	@PreAuthorize("hasRole('STORE')")
	@PostMapping
	public void save(@RequestBody Answer answer) {
		answerService.save(answer);
	}
	
	@PreAuthorize("hasRole('STORE')")
	@PutMapping("{id}")
	public void update(@PathVariable Long id,@RequestBody String content) {
		answerService.update(id, content);
	}
	
	@PreAuthorize("hasRole('STORE')")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		answerService.delete(id);
	}	
}