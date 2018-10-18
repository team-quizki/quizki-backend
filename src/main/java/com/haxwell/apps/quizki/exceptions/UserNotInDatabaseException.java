package com.haxwell.apps.quizki.exceptions;

public class UserNotInDatabaseException extends Throwable {
	
	private static final long serialVersionUID = 1L;
	
	private ValidationErrorData data;
	
	public UserNotInDatabaseException(ValidationErrorData data) {
		this.data = data;
		
	}
	
	public ValidationErrorData getData(){
		return this.data;
	}

}
