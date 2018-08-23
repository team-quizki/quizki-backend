package com.haxwell.apps.quizki.exceptions;

import java.util.HashMap;

public class UserFieldsNotUniqueException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private ValidationErrorData data;
	
	public UserFieldsNotUniqueException(ValidationErrorData data) {
		this.data = data;
		
	}
	
	public ValidationErrorData getData(){
		return this.data;
	}
	
}
