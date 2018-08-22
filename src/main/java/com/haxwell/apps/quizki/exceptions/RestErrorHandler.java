package com.haxwell.apps.quizki.exceptions;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.validation.FieldError;

@ControllerAdvice
public class RestErrorHandler {
	
	private MessageSource messageSource;
	
	@Autowired
	public RestErrorHandler(MessageSource ms) {
		this.messageSource = ms;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorData processValidationException( MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> errors = result.getFieldErrors();
		
		return processFieldErrors(errors);
	}
	
	private ValidationErrorData processFieldErrors(List<FieldError> errors) {
		ValidationErrorData data = new ValidationErrorData();
		for(FieldError err : errors) {
			data.addFieldError(err.getField(), resolveLocalMessage(err));
		}
		return data;
	}
	
	private String resolveLocalMessage(FieldError err) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String localErrMsg = messageSource.getMessage(err, currentLocale);
		
		
		//return the error code if the message is not available
		if(localErrMsg.equals(err.getDefaultMessage())) {
			String[] codes = err.getCodes();
			localErrMsg = codes[0];
		}
		
		return localErrMsg;
	}

}
