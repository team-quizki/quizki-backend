package com.haxwell.apps.quizki.exceptions;

public class UserRoleNotInDatabaseException extends RuntimeException {
	
	
	private static final long serialVersionUID = 1L;
	
	private ValidationErrorData data;
	
	public UserRoleNotInDatabaseException(ValidationErrorData data) {
		this.data = data;
		
	}
	
	public ValidationErrorData getData(){
		return this.data;
	}

}
