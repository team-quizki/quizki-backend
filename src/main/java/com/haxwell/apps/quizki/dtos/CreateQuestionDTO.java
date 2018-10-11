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
	
}
