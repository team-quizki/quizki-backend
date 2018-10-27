package com.haxwell.apps.quizki.exceptions;

public class CreateQuestionDTOException extends Throwable {
	
	private static final long serialVersionUID = 1L;
	
	private ValidationErrorData data;
	
	public CreateQuestionDTOException(ValidationErrorData data) {
		this.data = data;
		
	}
	
	public ValidationErrorData getData(){
		return this.data;
	}

}
