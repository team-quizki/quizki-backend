package com.haxwell.apps.quizki.services;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.HtmlUtils;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.any;
//Note this import added to avoid "any" naming collision in Mockito and Hamcrest Matchers see https://github.com/mockito/mockito/issues/1311

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import com.haxwell.apps.quizki.exceptions.GetQuestionException;
import com.haxwell.apps.quizki.repositories.QuestionRepository;
import com.haxwell.apps.quizki.repositories.ReferenceRepository;
import com.haxwell.apps.quizki.repositories.TopicRepository;
import com.haxwell.apps.quizki.repositories.UserRepository;

public class QuestionServiceImplTest {
	
	QuestionServiceImpl qsImpl;
	
	CreateQuestionDTO inputDTO;
	CreatedQuestionDTO outputDTO;
	
	private ArrayList<CreatedQuestionDTO> outputDTOs;
	
	Topic topic1;
	Reference reference1;
	Choice choice;
	static User user;
	static UserRole uRole;
	static Question question;
	List<Question> questions;
	
	static String role1 = "QUIZKI_USER_ROLE_ADMIN";
	static String role2 = "QUIZKI_USER_ROLE_USER";
	
	static Set<String> dtoTopics = new HashSet<String>();
	static Set<String> dtoRefs = new HashSet<String>();
	static Set<CreateChoiceDTO> dtoChoices = new HashSet<CreateChoiceDTO>();
	
	static Set<Topic> topics = new HashSet<Topic>();
	static Set<Reference> references = new HashSet<Reference>();
	static Set<Choice> choices = new HashSet<Choice>();
	
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
	
	private static Question getQuestion(long id) {
		
		uRole = new UserRole(role1);
		uRole.setId(1);
		
		String name = "Johnathan";
		String password = "password";
		String fullname = "Johnathan James";
		String email = "jjames@somewhere.com";
		String demographic = "default";
		
		user = new User(uRole, name, password, fullname, email, demographic);
		user.setId(userId);
		
		
		topics.add(new Topic(1l, topicStr1));
		references.add(new Reference(1l, refStr1));
		choices.add(new Choice(1l, choiceDTO1T.getText(), 1, choiceDTO1T.getIsCorrect()));
		
		
		Question outputQuestion = new Question(id, user, description, text, difficulty, type,
				references, topics, choices);
		
		return outputQuestion;
		
	}
	
	private static String appendInt(String s, int x) {
		return s + Integer.toString(x);
	}
	
	private List<Question> getQuestions(int n){
		
		questions = new ArrayList<Question>();
		
		uRole = new UserRole(role1);
		uRole.setId(1);
		
		String name = "Johnathan";
		String password = "password";
		String fullname = "Johnathan James";
		String email = "jjames@somewhere.com";
		String demographic = "default";
		user = new User(uRole, name, password, fullname, email, demographic);
		user.setId(userId);
		
		topics.add(new Topic(1l, topicStr1));
		references.add(new Reference(1l, refStr1));
		choices.add(new Choice(1l, choiceDTO1T.getText(), 1, choiceDTO1T.getIsCorrect()));
		
		for(int i = 1; i <= n; ++i) {
			questions.add(new Question((long) i, user, appendInt(description, i), appendInt(text, i), difficulty, type,
					references, topics, choices));
		}
		return questions;
	}
	
	private Page<Question> getPageQuestions(List<Question> ql, Pageable pgr ) {
		
		int pgn = pgr.getPageNumber();
		int pgsz = pgr.getPageSize();
		
		int strt = pgn * pgsz;
		int fin = strt + pgsz - 1;
		
		fin = Math.min(fin,ql.size() - 1);
		
		
		List<Question> subql = new ArrayList<>();
		
		for(int i = strt; i <= fin; ++i) {
			subql.add(ql.get(i));
		}

		Page<Question> pageQuestions = new PageImpl<Question>(subql, pgr, pgr.getPageSize());
		
		return pageQuestions;
		
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
		
		CreatedQuestionDTO outputDTO = null;
		
		uRole = new UserRole(role1);
		uRole.setId(1);
		
		String name = "Johnathan";
		String password = "password";
		String fullname = "Johnathan James";
		String email = "jjames@somewhere.com";
		String demographic = "default";
		
		
		
		user = new User(uRole, name, password, fullname, email, demographic);
		user.setId(userId);
		
		topic1 = new Topic(topicStr1);
		topic1.setId(1l);

		
		reference1 = new Reference(refStr1);
		reference1.setId(1l);
		
		Optional<User> userOpt = Optional.of(user);
	
		when(userRepo.findById(userId)).thenReturn(userOpt);
		
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
		
		assertNotNull(qsImpl);
		
		try {
			outputDTO = qsImpl.createQuestion(inputDTO);
		} catch (CreateQuestionDTOException e) {
			fail("createQuestion failed");
		}
		
		
		verify(userRepo).findById(userId);
		verify(topicRepo).findByText(topicStr1);
		verify(topicRepo).findByText(topicStr2);
		verify(refRepo).findByText(refStr1);
		verify(refRepo).findByText(refStr2);
		verify(questionRepo).save(any(Question.class));
		

		
		assertThat(outputDTO.getId(), equalTo(1l));
		assertThat(outputDTO.getUserId(), equalTo(userId));
		assertThat(outputDTO.getText(), equalTo(text));
		assertThat(outputDTO.getDescription(), equalTo(description));
		assertThat(outputDTO.getQuestionType(), equalTo(type));
		assertThat(outputDTO.getDifficulty(), equalTo(difficulty));
		
		assertThat(outputDTO.getChoices(), hasSize(2));
		assertThat(outputDTO.getTopics(), hasSize(2));
		assertThat(outputDTO.getReferences(), hasSize(2));
		
	}
	
	@Test
	public void getQuestionByIdTest() {
		
		question = getQuestion(1l);
		
		Optional<Question> inQuestionOpt = Optional.of(question);
		
		when(questionRepo.findById(1l)).thenReturn(inQuestionOpt);
		
		try {
			outputDTO = qsImpl.getQuestionById(Long.toString(1l));
		} catch (GetQuestionException e) {
			fail("getQuestionById failed");
		}
		
		assertThat(outputDTO.getId(), equalTo(1l));
		
	}
	
	@Test
	public void getQuestionsTest() {
		
		
		/*
		 * Stated requirements
		 * URL: /api/question?page=1&size=10
		 * URL: /api/question?page=1 (size defaults to 10)
		 * URL: /api/question?size=10 (page defaults to 1)
		 * URL: /api/question (page and size are default)
		 * 
		 * parameters are passed to getQuestions() as received by the controller with defaults
		 */
		
		int n = 17;		//TODO: refactor the magic numbers in the assertions based on this value
		questions = getQuestions(n);
		
		when(questionRepo.count()).thenReturn((long)n);
		
		when(questionRepo.findAll(any(Pageable.class))).thenAnswer(new Answer<Page<Question>>() {
			 
            public Page<Question> answer(InvocationOnMock invocation) throws Throwable {
                
            	
            	Pageable pageRequest = invocation.getArgument(0);
            	
                return getPageQuestions(questions, pageRequest);
            }
        });
		
		
		try {
			outputDTOs = qsImpl.getQuestions(1, 10);
		} catch (GetQuestionException e) {
			fail("getQuestions failed");
		}

		assertThat(outputDTOs.size(), equalTo(10));
		assertThat(outputDTOs.get(0).getId(), equalTo(1L));
		assertThat(outputDTOs.get(9).getId(), equalTo(10L));
		
		outputDTOs.clear();
		
		try {
			outputDTOs = qsImpl.getQuestions(3, 10);
		} catch (GetQuestionException e) {
			fail("getQuestions failed");
		}
		
		assertThat(outputDTOs.size(), equalTo(0));
		
		outputDTOs.clear();
		
		try {
			outputDTOs = qsImpl.getQuestions(2, 10);
		} catch (GetQuestionException e) {
			fail("getQuestions failed");
		}
		
		assertThat(outputDTOs.size(), equalTo(7));
		assertThat(outputDTOs.get(0).getId(), equalTo(11L));
		
		
		
	}

}
