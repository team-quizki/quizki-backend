package com.haxwell.apps.quizki.dtos;

import javax.validation.constraints.NotBlank;

/*
 * Object passed by front-end in the CreateQuestionDTO to create choices
 * {"text":"choiceText1", "isCorrect":true}
 * 
 */


public class CreateChoiceDTO {
	
	@NotBlank
	private String text;
	
	private boolean isCorrect;
	
	public CreateChoiceDTO(String text, boolean isCorrect) {
		super();
		this.text = text;
		this.isCorrect = isCorrect;
	}

	public CreateChoiceDTO() {
		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	public boolean getCorrect() {
		return this.isCorrect;
	}
	
}
