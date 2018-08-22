package com.haxwell.apps.quizki.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorData {
	
	ArrayList<FieldErrorData> errors = new ArrayList<FieldErrorData>();
	
	public ValidationErrorData() {		
	}
	
	public void addFieldError(String field, String message) {		
		errors.add(new FieldErrorData(field,message));		
	}
	
	public ArrayList<FieldErrorData> getErrors() {
		return errors;
	}

}
