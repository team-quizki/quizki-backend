package com.haxwell.apps.quizki.services;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.web.util.HtmlUtils;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.any;
//Note this import added to avoid "any" naming collision in Mockito and Hamcrest Matchers see https://github.com/mockito/mockito/issues/1311

import java.util.Optional;
import java.util.Set;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import com.haxwell.apps.quizki.dtos.CreateChoiceDTO;
import com.haxwell.apps.quizki.dtos.CreateQuestionDTO;
import com.haxwell.apps.quizki.dtos.CreatedQuestionDTO;
import com.haxwell.apps.quizki.entities.Choice;
import com.haxwell.apps.quizki.entities.Question;
import com.haxwell.apps.quizki.entities.Reference;
import com.haxwell.apps.quizki.entities.Topic;
import com.haxwell.apps.quizki.entities.User;
import com.haxwell.apps.quizki.entities.UserRole;
import com.haxwell.apps.quizki.exceptions.CreateQuestionDTOException;
import com.haxwell.apps.quizki.repositories.QuestionRepository;
import com.haxwell.apps.quizki.repositories.ReferenceRepository;
import com.haxwell.apps.quizki.repositories.TopicRepository;
import com.haxwell.apps.quizki.repositories.UserRepository;

public class QuestionServiceImplTest {
	
	QuestionServiceImpl qsImpl;
	
	CreateQuestionDTO inputDTO;
	CreatedQuestionDTO outputDTO;
	
	Topic topic1;
	Reference reference1;
	Choice choice;
	User user;
	UserRole uRole;
	Question question;
	
	String role1 = "QUIZKI_USER_ROLE_ADMIN";
	String role2 = "QUIZKI_USER_ROLE_USER";
	
	static Set<String> dtoTopics;
	static Set<String> dtoRefs;
	static Set<CreateChoiceDTO> dtoChoices;
	
	static String topicStr1 = "topic1";
	static String topicStr2 = "topic2";
	
	static String refStr1 = "reference1";
	static String refStr2 = "reference2";
	
	static String text = "question text";
	static String description = "question description";
	
	static long userId = 1;
	static int type = 1;
	static int difficulty = 1;
	
	static CreateChoiceDTO choiceDTO1T = new CreateChoiceDTO("choice1",true);
	static CreateChoiceDTO choiceDTO2F = new CreateChoiceDTO("choice2", false);
	
	
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
		
		dtoTopics.add(topicStr1);
		dtoTopics.add(topicStr2);
		
		dtoRefs.add(refStr1);
		dtoRefs.add(refStr2);

		dtoChoices.add(choiceDTO1T);
		dtoChoices.add(choiceDTO2F);
	
		inputDTO = new CreateQuestionDTO(userId, text, description, type, dtoTopics, dtoRefs, difficulty, dtoChoices);
		
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
		
		uRole = new UserRole(role1);
		uRole.setId(1);
		
		String name = "Johnathan";
		String password = "password";
		String fullname = "Johnathan James";
		String email = "jjames@somewhere.com";
		String demographic = "default";
		long uId = 2;
		
		
		user = new User(uRole, name, password, fullname, email, demographic);
		user.setId(uId);
		
		topic1 = new Topic(topicStr1);
		topic1.setId(1l);

		
		reference1 = new Reference(refStr1);
		reference1.setId(1l);
		
		Optional<User> userOpt = Optional.of(user);
	
		when(userRepo.findById(uId)).thenReturn(userOpt);
		
		when(topicRepo.findByText(topicStr1)).thenReturn(topic1);
		when(topicRepo.findByText(topicStr2)).thenReturn(null);
		
		when(refRepo.findByText(refStr1)).thenReturn(reference1);
		when(refRepo.findByText(refStr2)).thenReturn(null);
		
		//Stubbing the save method inserts ID value 2 in the (null) Topics & Refs and sets ids in Question parameter & choices
		//to emulate JPA cascading in the returned Question
		//Note: Both the topics and references returned here will not have Questions which are not relevant to outputDTO
		
		when(questionRepo.save(any(Question.class))).thenAnswer(new Answer<Question>() {
 
            public Question answer(InvocationOnMock invocation) throws Throwable {
                Question question = invocation.getArgument(0);
                question.setId(1l);
                
                for(Topic t: question.getTopics()) {
                	if(t.getId() == null)
                		t.setId(2l);
                }
                
                for(Reference r: question.getReferences()) {
                	if(r.getId() == null)
                		r.setId(2l);
                }
                
                int i = 1;
                for(Choice c: question.getChoices()) {
                	c.setId(i);
                	++i;
                }

                return question;
            }
        });
		
		
		
		
		CreatedQuestionDTO outputDTO = qsImpl.createQuestion(inputDTO);
		
		verify(userRepo).findById(uId);
		verify(topicRepo).findByText(topicStr1);
		verify(topicRepo).findByText(topicStr2);
		verify(refRepo).findByText(refStr1);
		verify(refRepo).findByText(refStr2);
		verify(questionRepo).save(any(Question.class));
		
		//TODO: assert that the outputDTO has the correct properties
		
		assertThat(outputDTO.getId(), equalTo(1l));
		assertThat(outputDTO.getUserId(), equalTo(uId));
		assertThat(outputDTO.getText(), equalTo(text));
		assertThat(outputDTO.getDescription(), equalTo(description));
		assertThat(outputDTO.getQuestionType(), equalTo(type));
		assertThat(outputDTO.getDifficulty(), equalTo(difficulty));
		
		assertThat(outputDTO.getChoices(), hasSize(2));
		assertThat(outputDTO.getTopics(), hasSize(2));
		assertThat(outputDTO.getReferences(), hasSize(2));
		
	}

}
