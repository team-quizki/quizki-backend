package com.haxwell.apps.quizki.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.haxwell.apps.quizki.dtos.CreateQuestionDTO;
import com.haxwell.apps.quizki.dtos.CreatedQuestionDTO;
import com.haxwell.apps.quizki.exceptions.CreateQuestionDTOException;
import com.haxwell.apps.quizki.services.QuestionService;

@CrossOrigin (origins = "http://localhost:4200")
@RestController
@RequestMapping(value = {"/api/question"})
public class QuestionController {

	@Autowired
	private QuestionService qs;
	
	public QuestionController (QuestionService qs) {
		this.qs = qs;
	}
	
	@PostMapping
	@ResponseBody
	public CreatedQuestionDTO create(@RequestBody @Valid CreateQuestionDTO cqDTO) throws CreateQuestionDTOException {
		 return qs.createQuestion(cqDTO);
	}
}
