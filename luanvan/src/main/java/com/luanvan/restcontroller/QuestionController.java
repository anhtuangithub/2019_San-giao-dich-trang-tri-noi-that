package com.luanvan.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luanvan.dto.response.QuestionResponseDTO;
import com.luanvan.model.Question;
import com.luanvan.service.QuestionService;

@RestController
@RequestMapping("questions")
public class QuestionController {

	private QuestionService questionService;
	
	@Autowired
	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}
	
	@GetMapping
	public List<Question> findAllQuestion(){
		return questionService.findAllQuestion();
	}
	
	@GetMapping("/{id}")
	public QuestionResponseDTO findQuestionById(@PathVariable Long id){
		return questionService.questionById(id);
	}
	
	@GetMapping("detail-of-product/{productid}")
	public List<QuestionResponseDTO> findQuestionHome(@PathVariable Long productid){
		return questionService.findAllQuestionHome(productid, 1);
	}
	
	@GetMapping("question-of-user")
	public Page<QuestionResponseDTO> questionOfUser(Authentication auth,@RequestParam(value ="page", required = false, defaultValue = "0") int page){
		return questionService.questionByUser(auth, page);
	}
	
	@PostMapping
	public void save(@RequestBody Question question,Authentication auth) {
		questionService.save(question,auth);
	}
	
	@PutMapping("/{id}")
	public void update(@PathVariable Long id) {
		questionService.changeStatus(id);
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		questionService.delete(id);
	}
	
	@GetMapping("cau-hoi-cua-store")
	public List<Question> QuestionByStore(Authentication auth){
		return questionService.listQuestionByStore(auth);
	}
	
	@GetMapping("cau-hoi-cua-user")
	public List<Question> QuestionOfUser(Authentication auth){
		return questionService.listQuestionByStore(auth);
	}
	
	@GetMapping("cau-hoi-cua-store-reponse")
	public List<QuestionResponseDTO> QuestionDTOByStore(Authentication auth){
		return questionService.listQuestionDTOByStore(auth);
	}
	
	@GetMapping("tat-ca-cau-hoi")
	public Page<QuestionResponseDTO> questionHoem(@RequestParam("productid") Long productid,
			@RequestParam(value ="page", required = false, defaultValue = "0") int page){
		return questionService.AllQuestionHome(productid, page);
	}
}
