package com.haxwell.apps.quizki.dtos;

import java.util.Set;

public class CreateQuestionDTO {

	/*
	 * This is the object passed by the frontend to create a question
	 * 
	 * {
	 * "userId": 23,
	 * "text": "RequiredQuestionText",
	 * "description": "",
	 * "type": 1,
	 * "topics": ["string1", "string2", "stringN"],
	 * "references": ["ref1", "ref2", "refN"],
	 * "difficulty": 1,
	 * "choices": [{"text":"choiceText1", "isCorrect":true}, {"text":"choiceText2", "isCorrect":false}]
	 * }
	 * 
	 */
	
	private long userId;
	private String text;
	private String description;
	private int type;
	private Set<String> topics;
	private Set<String> references;
	private int difficulty;
	private Set<CreateChoiceDTO> choices;
	
	public CreateQuestionDTO(long userId, String text, String description, int type, Set<String> topics,
			Set<String> references, int difficulty, Set<CreateChoiceDTO> choices) {
//		super();
		this.userId = userId;
		this.text = text;
		this.description = description;
		this.type = type;
		this.topics = topics;
		this.references = references;
		this.difficulty = difficulty;
		this.choices = choices;
	}

	public CreateQuestionDTO() {
		
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Set<String> getTopics() {
		return topics;
	}

	public void setTopics(Set<String> topics) {
		this.topics = topics;
	}

	public Set<String> getReferences() {
		return references;
	}

	public void setReferences(Set<String> references) {
		this.references = references;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public Set<CreateChoiceDTO> getChoices() {
		return choices;
	}

	public void setChoices(Set<CreateChoiceDTO> choices) {
		this.choices = choices;
	}
	
	
}
