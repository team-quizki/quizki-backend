package com.haxwell.apps.quizki.services;

import com.haxwell.apps.quizki.dtos.CreateQuestionDTO;
import com.haxwell.apps.quizki.dtos.CreatedQuestionDTO;
import com.haxwell.apps.quizki.exceptions.UserNotInDatabaseException;

public interface QuestionService {
	public CreatedQuestionDTO createQuestion(CreateQuestionDTO cqDTO) throws UserNotInDatabaseException;

}
