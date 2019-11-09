package com.luanvan.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.luanvan.dto.response.QuestionResponseDTO;
import com.luanvan.exception.NotFoundException;
import com.luanvan.model.Question;
import com.luanvan.model.Users;
import com.luanvan.repo.QuestionRepository;
import com.luanvan.repo.StoreRepository;
import com.luanvan.repo.UsersRepository;
import com.luanvan.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

	private QuestionRepository questionRepository;
	private UsersRepository userRepository;
	private StoreRepository storeRepository;
	
	@Autowired
	public QuestionServiceImpl(QuestionRepository questionRepository,
			UsersRepository userRepository,
			StoreRepository storeRepository) {
		this.questionRepository = questionRepository;
		this.userRepository = userRepository;
		this.storeRepository = storeRepository;
	}

	@Override
	public List<Question> findAllQuestion() {
		return questionRepository.findAll();
	}

	@Override
	public void save(Question question,Authentication auth) {
		Users user = userRepository.findByEmail(auth.getName());
		question.setUser(user);
		questionRepository.save(question);
		
	}

	@Override
	public void delete(Long id) {
		questionRepository.deleteById(id);
		
	}

	@Override
	public Optional<Question> findById(Long id) {
		return questionRepository.findById(id);
	}

	@Override
	public void changeStatus(Long id) {
		Question question = questionRepository.findById(id).orElseThrow(NotFoundException::new);
		if(question.getStatus() == 1) {
			question.setStatus(0);
		}
		else question.setStatus(1);
		questionRepository.save(question);
		
	}

	@Override
	public List<QuestionResponseDTO> findAllQuestionHome(Long productid, int status) {
		List<Question> questions = questionRepository.findByProductsIdAndStatus(productid, status);
		ModelMapper mapper = new ModelMapper();
		List<QuestionResponseDTO> questionDTO = mapper.map(questions,new TypeToken<List<QuestionResponseDTO>>(){}.getType());
		return questionDTO.stream()
				.limit(5).collect(Collectors.toList());
	}

	@Override
	public List<Question> listQuestionByStore(Authentication auth) {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Long storeid = storeRepository.findByUsersId(userid).getId();
		return questionRepository.listQuestionStore(storeid);
	}

	@Override
	public Page<QuestionResponseDTO> questionByUser(Authentication auth, int page) {
		int pageminus = 0;
		if(page>=1) {
			pageminus = page-1;
		}
		Pageable sorted =  PageRequest.of(pageminus, 1);
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Page<Question> questions = questionRepository.findByUserIdOrderByIdDesc(userid,sorted);
		Page<QuestionResponseDTO> questionDTOS = questions.map(new Function<Question, QuestionResponseDTO>() {
		    @Override
		    public QuestionResponseDTO apply(Question entity) {
		    	ModelMapper mapper = new ModelMapper();
		    	QuestionResponseDTO questionDTO = mapper.map(entity,QuestionResponseDTO.class);
		        return questionDTO;
		    }
		});
		
		return questionDTOS;
	}

	@Override
	public Page<QuestionResponseDTO> AllQuestionHome(Long productid, int page) {
		int pageminus = 0;
		if(page>=1) {
			pageminus = page-1;
		}
		Pageable sorted =  PageRequest.of(pageminus, 1);
		Page<Question> questions = questionRepository.findByProductsIdAndStatusOrderByIdDesc(productid, 1, sorted);
		Page<QuestionResponseDTO> questionDTOS = questions.map(new Function<Question, QuestionResponseDTO>() {
		    @Override
		    public QuestionResponseDTO apply(Question entity) {
		    	ModelMapper mapper = new ModelMapper();
		    	QuestionResponseDTO questionDTO = mapper.map(entity,QuestionResponseDTO.class);
		        return questionDTO;
		    }
		});
		
		return questionDTOS;

	}
}
