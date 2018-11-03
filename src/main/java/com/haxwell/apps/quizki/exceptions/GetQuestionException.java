package com.haxwell.apps.quizki.exceptions;

public class GetQuestionException extends Throwable {
	private static final long serialVersionUID = 1L;
	
	private ValidationErrorData data;
	
	public GetQuestionException(ValidationErrorData data) {
		this.data = data;
		
	}
	
	public ValidationErrorData getData(){
		return this.data;
	}
}
