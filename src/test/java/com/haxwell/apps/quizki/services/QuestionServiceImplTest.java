package com.haxwell.apps.quizki.services;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.Set;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.haxwell.apps.quizki.dtos.CreateChoiceDTO;
import com.haxwell.apps.quizki.dtos.CreateQuestionDTO;
import com.haxwell.apps.quizki.dtos.CreatedQuestionDTO;
import com.haxwell.apps.quizki.entities.Choice;
import com.haxwell.apps.quizki.entities.Reference;
import com.haxwell.apps.quizki.entities.Topic;
import com.haxwell.apps.quizki.exceptions.CreateQuestionDTOException;
import com.haxwell.apps.quizki.repositories.QuestionRepository;
import com.haxwell.apps.quizki.repositories.ReferenceRepository;
import com.haxwell.apps.quizki.repositories.TopicRepository;
import com.haxwell.apps.quizki.repositories.UserRepository;

public class QuestionServiceImplTest {
	
	QuestionServiceImpl qsImpl;
	
	CreateQuestionDTO inputDTO;
	CreatedQuestionDTO outputDTO;
	
	Topic topic;
	Reference reference;
	Choice choice;
	
	static Set<String> dtoTopics;
	static Set<String> dtoRefs;
	static Set<CreateChoiceDTO> dtoChoices;
	
	@Mock
	private QuestionRepository questionRepo;
	
	@Mock
	private TopicRepository topicRepo;
	
	@Mock
	private ReferenceRepository refRepo;
		
	@Mock
	private UserRepository userRepo;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);		
		qsImpl = new QuestionServiceImpl(questionRepo, topicRepo, refRepo, userRepo);
	}
	
	private static CreateQuestionDTO getBaseInputDTO() {
		
		CreateQuestionDTO inputDTO;
		
		String topic1 = "topic1";
		String topic2 = "topic2";
		dtoTopics.add(topic1);
		dtoTopics.add(topic2);
		
		String ref1 = "reference1";
		String ref2 = "reference2";
		dtoRefs.add(ref1);
		dtoRefs.add(ref2);
		
		CreateChoiceDTO choiceDTO1T = new CreateChoiceDTO("choice1",true);
		CreateChoiceDTO choiceDTO2F = new CreateChoiceDTO("choice2", false);
		dtoChoices.add(choiceDTO1T);
		dtoChoices.add(choiceDTO2F);
		
		String text = "question text";
		String desc = "question description";
		
		long userId = 1;
		int type = 1;
		int difficulty = 1;
		
		inputDTO = new CreateQuestionDTO(userId, text, desc, type, dtoTopics, dtoRefs, difficulty, dtoChoices);
		
		return inputDTO;
	}
	
	@Test
	public void mockCreationTest() {
		assertNotNull(questionRepo);
		assertNotNull(topicRepo);
		assertNotNull(refRepo);
		assertNotNull(userRepo);
	}
	
	
	
	
	@Test
	public void createQuestionWithValidUserTest() throws CreateQuestionDTOException {
		
		CreateQuestionDTO inputDTO = getBaseInputDTO();
		
		CreatedQuestionDTO outputDTO = qsImpl.createQuestion(inputDTO);
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
