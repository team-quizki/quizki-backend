package com.haxwell.apps.quizki.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
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
import com.haxwell.apps.quizki.exceptions.UserNotInDatabaseException;
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
	private Set<CreateChoiceDTO> choiceDTOs;
	
	private Question question;
	private Question savedQuestion;
	
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
	
	public CreatedQuestionDTO createQuestion(CreateQuestionDTO cqDTO) throws UserNotInDatabaseException {
		
		this.question = new Question();
		this.outputDTO = new CreatedQuestionDTO();
		
		Optional<Topic> aTopic = null;
		Optional<Reference> aRef = null;
		
		Set<Question> setQuestion = new HashSet<Question>();
		setQuestion.add(this.question);
		
		
		this.user = userRepo.findById(cqDTO.getUserId());
		
		if(!user.isPresent()) {
			
			ValidationErrorData data = new ValidationErrorData();
			data.addFieldError("user", "user.not.in.database");
			throw new UserNotInDatabaseException(data);
			
		}
		
		this.question.setUser(user.get());
		this.outputDTO.setUserId(user.get().getId());
		
		this.text = cqDTO.getText();
		this.question.setText(HtmlUtils.htmlEscape(this.text));
		
		this.description = cqDTO.getDescription();
		this.question.setDescription(this.description);
		this.outputDTO.setDescription(this.description);
		
		this.questionType = cqDTO.getType();
		this.question.setQuestionType(this.questionType);
		this.outputDTO.setQuestionType(this.questionType);
		
		this.difficulty = cqDTO.getDifficulty();
		this.question.setDifficulty(this.difficulty);
		this.outputDTO.setDifficulty(this.difficulty);
		
		this.choiceDTOs = cqDTO.getChoices();
		for(CreateChoiceDTO ccDTO : this.choiceDTOs) {
			Choice choice = new Choice();
			choice.setSequence(0);		//default for Single & Multiple types need logic here for other question types
			choice.setCorrect(ccDTO.getCorrect());
			choice.setText(ccDTO.getText());
			this.question.getChoices().add(choice);
		}
		
		
		this.topicStrings = cqDTO.getTopics();
		for(String ts: this.topicStrings) {
			aTopic = Optional.of(topicRepo.findByText(ts.toLowerCase()));
			if(aTopic.isPresent()) {
				this.question.getTopics().add(aTopic.get());
			} else {
				Topic newTopic = new Topic(ts.toLowerCase());
				newTopic.setQuestions(setQuestion);
				this.question.getTopics().add(newTopic);
			}
		}
		

		this.refStrings = cqDTO.getReferences();
		for(String rs: this.refStrings) {
			aRef = Optional.of(refRepo.findByText(HtmlUtils.htmlEscape(rs)));
			if(aRef.isPresent()) {
				this.question.getReferences().add(aRef.get());
			} else {
				Reference newRef = new Reference(HtmlUtils.htmlEscape(rs));
				newRef.setQuestions(setQuestion);
				this.question.getReferences().add(newRef);
			}
			
		}
		
		savedQuestion = this.questionRepo.save(this.question);
		
		outputDTO.setChoices(this.savedQuestion.getChoices());
		outputDTO.setTopics(this.savedQuestion.getTopics());
		for(Reference r: this.savedQuestion.getReferences()) {
			r.setText(HtmlUtils.htmlUnescape(r.getText()));
		}
		outputDTO.setReferences(this.savedQuestion.getReferences());
		outputDTO.setText(HtmlUtils.htmlUnescape(this.savedQuestion.getText()));
		
		return outputDTO;
	}
	
	
}