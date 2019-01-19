package com.haxwell.apps.quizki.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.haxwell.apps.quizki.dtos.CreateChoiceDTO;
import com.haxwell.apps.quizki.dtos.CreateQuestionDTO;
import com.haxwell.apps.quizki.dtos.CreatedQuestionDTO;
import com.haxwell.apps.quizki.entities.Choice;
import com.haxwell.apps.quizki.entities.Question;
import com.haxwell.apps.quizki.entities.Reference;
import com.haxwell.apps.quizki.entities.Topic;
import com.haxwell.apps.quizki.entities.User;
import com.haxwell.apps.quizki.exceptions.CreateQuestionDTOException;
import com.haxwell.apps.quizki.exceptions.GetQuestionException;
import com.haxwell.apps.quizki.exceptions.ValidationErrorData;
import com.haxwell.apps.quizki.repositories.QuestionRepository;
import com.haxwell.apps.quizki.repositories.ReferenceRepository;
import com.haxwell.apps.quizki.repositories.TopicRepository;
import com.haxwell.apps.quizki.repositories.UserRepository;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	protected long id;
	private Optional<User> user;
	private String description;
	private String text;
	private int difficulty;
	private int questionType;
	private Set<String> refStrings = new HashSet<String>();
	private Set<String> topicStrings = new HashSet<String>();
	
	private CreatedQuestionDTO outputDTO;
	private ArrayList<CreatedQuestionDTO> outputDTOs;
	private Set<CreateChoiceDTO> choiceDTOs;
	
	private Question question;
	private Question savedQuestion;
	private Optional<Question> outQuestion;
	private Page<Question> questions;
	
	@Autowired
	private QuestionRepository questionRepo;
	
	@Autowired
	private TopicRepository topicRepo;
	
	@Autowired
	private ReferenceRepository refRepo;
		
	@Autowired
	private UserRepository userRepo;

	public QuestionServiceImpl(QuestionRepository questionRepo, TopicRepository topicRepo, ReferenceRepository refRepo,
			 UserRepository userRepo) {
		super();
		this.questionRepo = questionRepo;
		this.topicRepo = topicRepo;
		this.refRepo = refRepo;
		this.userRepo = userRepo;
	}
	
	public CreatedQuestionDTO createQuestion(CreateQuestionDTO cqDTO) throws CreateQuestionDTOException {
		
		this.question = new Question();
		this.outputDTO = new CreatedQuestionDTO();
		
		Topic aTopic = null;
		Reference aRef = null;
		
		Set<Topic> topics = new HashSet<Topic>();
		Set<Reference> references = new HashSet<Reference>();
		Set<Choice> choices = new HashSet<Choice>();
		
		Set<Question> setQuestions = new HashSet<Question>();

		
		
		this.user = userRepo.findById(cqDTO.getUserId());
		
		if(!user.isPresent()) {
			
			ValidationErrorData data = new ValidationErrorData();
			data.addFieldError("user", "user.not.valid");
			throw new CreateQuestionDTOException(data);
			
		}
		
		this.question.setUser(user.get());
		this.outputDTO.setUserId(user.get().getId());
		
		this.text = cqDTO.getText();
		this.question.setText(HtmlUtils.htmlEscape(this.text));
		
		this.description = cqDTO.getDescription();
		this.question.setDescription(this.description);
		this.outputDTO.setDescription(this.description);
		
		this.questionType = cqDTO.getType();
		//frontend will send a 0 if the user has not selected a type
		if(this.questionType == 0) {
			
			ValidationErrorData data = new ValidationErrorData();
			data.addFieldError("type", "type.not.selected");
			throw new CreateQuestionDTOException(data);
			
			
		}
		
		if(this.questionType > 1) {
			
			ValidationErrorData data = new ValidationErrorData();
			data.addFieldError("type", "type.not.valid");
			throw new CreateQuestionDTOException(data);
			
			
		}

		this.question.setQuestionType(this.questionType);
		this.outputDTO.setQuestionType(this.questionType);
		
		this.difficulty = cqDTO.getDifficulty();
		if(this.difficulty < 0 || this.difficulty > 5) {
			
			ValidationErrorData data = new ValidationErrorData();
			data.addFieldError("difficulty", "difficulty.not.in.difficulties");
			throw new CreateQuestionDTOException(data);
			
			
		}

		this.question.setDifficulty(this.difficulty);
		this.outputDTO.setDifficulty(this.difficulty);
		
		
		this.question.setTopics(topics);
		this.question.setReferences(references);
		this.question.setChoices(choices);
		
		
		this.choiceDTOs = cqDTO.getChoices();

		int isCorrects = 0;
		
		for(CreateChoiceDTO ccDTO : this.choiceDTOs) {
			Choice choice = new Choice();
			choice.setSequence(0);		//TODO: add handlers for other question types here
			choice.setIsCorrect(ccDTO.getIsCorrect());
			if(choice.getIsCorrect() == true)
				++isCorrects;
			choice.setText(ccDTO.getText());			
			this.question.addChoice(choice);
		
		}
		
		if(isCorrects == 0) {
			ValidationErrorData data = new ValidationErrorData();
			data.addFieldError("choices", "choices.choice.isCorrect.not.true");
			throw new CreateQuestionDTOException(data);
		}


		
		
		this.topicStrings = cqDTO.getTopics();
		for(String ts: this.topicStrings) {
			aTopic = topicRepo.findByText(ts.toLowerCase());
			
			if(aTopic != null) {
				this.question.addTopic(aTopic);
			} else {
				
				Topic newTopic = new Topic(ts.toLowerCase());
				newTopic.setQuestions(setQuestions);				
				newTopic.addQuestion(this.question);				
				this.question.addTopic(newTopic);					
			}
		}
		

		this.refStrings = cqDTO.getReferences();
		for(String rs: this.refStrings) {
			aRef = refRepo.findByText(HtmlUtils.htmlEscape(rs));
			if(aRef != null) {
				this.question.addReference(aRef);
			} else {
				Reference newRef = new Reference(HtmlUtils.htmlEscape(rs));
				newRef.setQuestions(setQuestions);
				newRef.addQuestion(this.question);
				this.question.addReference(newRef);
			}
			
		}
		
		
		this.savedQuestion = this.questionRepo.save(this.question);
		
		outputDTO.setId(this.savedQuestion.getId());
		
		outputDTO.setChoices(this.savedQuestion.getChoices());
		outputDTO.setTopics(this.savedQuestion.getTopics());
		
		for(Reference r: this.savedQuestion.getReferences()) {
			r.setText(HtmlUtils.htmlUnescape(r.getText()));
		}
		outputDTO.setReferences(this.savedQuestion.getReferences());
		outputDTO.setText(HtmlUtils.htmlUnescape(this.savedQuestion.getText()));
		
		return outputDTO;
	}
	
	public CreatedQuestionDTO getQuestionById(String idString) throws GetQuestionException {
		
		long id = Long.parseLong(idString);
		
		outQuestion = questionRepo.findById(id);
		
		if(outQuestion.isPresent()) {
			
			outputDTO = new CreatedQuestionDTO();
			question = outQuestion.get();
			
			outputDTO.setId(question.getId());
			outputDTO.setUserId(question.getUser().getId());
			outputDTO.setDescription(question.getDescription());
			outputDTO.setText(question.getText());
			outputDTO.setDifficulty(question.getDifficulty());
			outputDTO.setQuestionType(question.getQuestionType());
			outputDTO.setTopics(question.getTopics());
			outputDTO.setReferences(question.getReferences());
			outputDTO.setChoices(question.getChoices());
			
			
		} else {
			
			ValidationErrorData data = new ValidationErrorData();
			data.addFieldError("id", "question id not found");
			throw new GetQuestionException(data);
		}
		
		return outputDTO;
		
	}
	
	
	public ArrayList<CreatedQuestionDTO> getQuestions(int page, int size) throws GetQuestionException {
	
		Pageable pageable = null;
		
		outputDTOs = new ArrayList<CreatedQuestionDTO>();
		
		long qs = questionRepo.count();
		
		if((page - 1) * size <= qs) {
			
			pageable = PageRequest.of((page - 1), size);
			
		} else {
			return outputDTOs;		//return the empty array if no data exists
		}
			
		questions = questionRepo.findAll(pageable);
		
		System.out.println("Mock questionRepo.findAll returned <Page>questions.getSize: " + questions.getSize() + " .getTotalElements: " + questions.getTotalElements());
		
		outputDTOs.clear();
		
		
		for(Question q : questions.getContent()) {		//TODO: <----- <Page>questions here has a size of 10!!!!
			
			outputDTO = new CreatedQuestionDTO();
			
			outputDTO.setId(q.getId());
			outputDTO.setUserId(q.getUser().getId());
			outputDTO.setDescription(q.getDescription());
			outputDTO.setText(q.getText());
			outputDTO.setDifficulty(q.getDifficulty());
			outputDTO.setQuestionType(q.getQuestionType());
			outputDTO.setTopics(q.getTopics());
			outputDTO.setReferences(q.getReferences());
			outputDTO.setChoices(q.getChoices());
			
			outputDTOs.add(outputDTO);
		}

		System.out.println("After DTO creation loop outputDTOs size: " + outputDTOs.size()); //<------- but outputDTOs has a size of 17!!!!
		
		/*TODO: QuestionServiceImplTest mocks questionRepo.findAll(pageable) (lines 345-356) and the log shows that questions has a size of 10
		 * 		but the for loop above produces an ArrayList of size 17!!!! WTF???
		 */
		
		
		
		
		
		return outputDTOs;
		
	}
	
}
