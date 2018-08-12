package com.haxwell.apps.quizki.exceptions;

import java.util.HashMap;

public class UserFieldsNotUniqueException extends RuntimeException {
	private long emailCount;
	private long nameCount;
	
	public UserFieldsNotUniqueException(long ecount, long ncount) {
		this.emailCount = ecount;
		this.nameCount = ncount;
	}
	
	public long getEmailCount(){
		return this.emailCount;
	}
	
	public long getNameCount(){
		return this.nameCount;
	}
}
