package com.haxwell.apps.quizki.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haxwell.apps.quizki.dtos.CreateChoiceDTO;
import com.haxwell.apps.quizki.dtos.CreateQuestionDTO;
import com.haxwell.apps.quizki.dtos.CreatedQuestionDTO;
import com.haxwell.apps.quizki.entities.Choice;
import com.haxwell.apps.quizki.entities.Question;
import com.haxwell.apps.quizki.entities.Reference;
import com.haxwell.apps.quizki.entities.Topic;
import com.haxwell.apps.quizki.entities.User;
import com.haxwell.apps.quizki.exceptions.UserRoleNotInDatabaseException;
import com.haxwell.apps.quizki.exceptions.ValidationErrorData;
import com.haxwell.apps.quizki.repositories.ChoiceRepository;
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
	private Set<Reference> references = new HashSet<Reference>();
	private Set<Topic> topics = new HashSet<Topic>();
	private Set<Choice> choices = new HashSet<Choice>();
	
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
	private ChoiceRepository choiceRepo;
	
	@Autowired
	private UserRepository userRepo;

	public QuestionServiceImpl(QuestionRepository questionRepo, TopicRepository topicRepo, ReferenceRepository refRepo,
			ChoiceRepository choiceRepo, UserRepository userRepo) {
//		super();
		this.questionRepo = questionRepo;
		this.topicRepo = topicRepo;
		this.refRepo = refRepo;
		this.choiceRepo = choiceRepo;
		this.userRepo = userRepo;
	}
	
	public CreatedQuestionDTO createQuestion(CreateQuestionDTO cqDTO) {
		
		this.question = new Question();
		this.outputDTO = new CreatedQuestionDTO();
		
		
		//TODO: check if this needs to be in a try/catch for DB access problems
		this.user = userRepo.findById(cqDTO.getUserId());
		
		if(!user.isPresent()) {
			/*
			ValidationErrorData data = new ValidationErrorData();
			data.addFieldError("role", "role.not.in.database");
			throw new UserRoleNotInDatabaseException(data);
			
			add Validation and exception code here...	
			*/
		}
		
		this.question.setUser(user.get());
		this.outputDTO.setUserId(user.get().getId());
		
		this.text = cqDTO.getText();
		this.question.setText(this.text);
		this.outputDTO.setText(this.text);
		
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
		
		//TODO: after the question is saved and the saved version returned get the newly created choices and add them to the DTO
		
		return outputDTO;
	}
	
	
}
