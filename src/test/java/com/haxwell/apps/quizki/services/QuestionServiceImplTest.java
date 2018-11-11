package com.haxwell.apps.quizki.services;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.web.util.HtmlUtils;

import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Set;
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
		
		uRole = new UserRole(role1);
		uRole.setId(1);
		
		String name = "Johnathan";
		String password = "password";
		String fullname = "Johnathan James";
		String email = "jjames@somewhere.com";
		String demographic = "default";
		long id = 1;
		
		
		
		user = new User(uRole, name, password, fullname, email, demographic);
		user.setId(1l);
		
		topic1 = new Topic("topic1");
		topic1.setId(1l);
		//TODO: determine if topic1.questions needs to be populated for testing
		
		reference1 = new Reference("reference1");
		reference1.setId(1l);
		
		Optional<User> userOpt = Optional.of(user);
	
		when(userRepo.findById(id)).thenReturn(userOpt);
		
		when(topicRepo.findByText("topic1")).thenReturn(topic1);
		when(topicRepo.findByText("topic2")).thenReturn(null);
		
		when(refRepo.findByText("reference1")).thenReturn(reference1);
		when(refRepo.findByText("reference2")).thenReturn(null);
		
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
		
		verify(userRepo).findById(id);
		verify(topicRepo).findByText("topic1");
		verify(topicRepo).findByText("topic2");
		verify(refRepo).findByText("reference1");
		verify(refRepo).findByText("reference2");
		verify(questionRepo).save(any(Question.class));
		
		
		
		
		
		
		
		
		
		
	}

}
