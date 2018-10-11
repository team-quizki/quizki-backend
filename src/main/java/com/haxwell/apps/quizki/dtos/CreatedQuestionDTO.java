package com.haxwell.apps.quizki.dtos;

import java.util.Set;

import com.haxwell.apps.quizki.entities.Choice;
import com.haxwell.apps.quizki.entities.Reference;
import com.haxwell.apps.quizki.entities.Topic;
import com.haxwell.apps.quizki.entities.User;

public class CreatedQuestionDTO {
	
	/*
	 * This is the Data Transfer Object that is passed to the frontend on creation of the question
	 * 
	 * "id": 263,
	 * "userId": 23,
	 * "text": "RequiredQuestionText",
	 * "description": "",
	 * "type": 1,
	 * "topics": [{"id":85, "text":"string1"}, {"id":84, "text":"string2"}, {"id":89, "text":"stringN"}],
	 * "references": [{"id":1191, "text":"ref1"}, {"id":1163, "text":"ref2"}, {"id":2823, "text":"refN"}],
	 * "difficulty": 1,
	 * "choices": [{"id":737, "text":"choiceText1", "isCorrect":true}, {"id":982, "text":"choiceText2", "isCorrect":false}]
	 * 
	 * 
	 */
	
	private long id;
	private long userId;
	private String description;
	private String text;
	private int difficulty;
	private int questionType;	
	private Set<Reference> references;
	private Set<Topic> topics;
	private Set<Choice> choices;
	
	public CreatedQuestionDTO(long id, long userId, String description, String text, int difficulty, int questionType,
			Set<Reference> references, Set<Topic> topics, Set<Choice> choices) {
		this.id = id;
		this.userId = userId;
		this.description = description;
		this.text = text;
		this.difficulty = difficulty;
		this.questionType = questionType;
		this.references = references;
		this.topics = topics;
		this.choices = choices;
	}

	public CreatedQuestionDTO() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public Set<Reference> getReferences() {
		return references;
	}

	public void setReferences(Set<Reference> references) {
		this.references = references;
	}

	public Set<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}

	public Set<Choice> getChoices() {
		return choices;
	}

	public void setChoices(Set<Choice> choices) {
		this.choices = choices;
	}
	
}
