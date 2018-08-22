package com.haxwell.apps.quizki.exceptions;

public class FieldErrorData {
	public String field;
	public String message;
	
	public FieldErrorData(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}
	
	
}
