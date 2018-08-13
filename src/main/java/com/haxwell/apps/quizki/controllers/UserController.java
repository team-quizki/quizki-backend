package com.haxwell.apps.quizki.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.haxwell.apps.quizki.entities.User;
import com.haxwell.apps.quizki.exceptions.UserFieldsNotUniqueException;
import com.haxwell.apps.quizki.repositories.UserRepository;

@CrossOrigin (origins = "http://localhost:4200")
@RestController
@RequestMapping ( value = {"/api/users"} )
public class UserController {
	
	private final UserRepository ur;
	
	@Autowired
	UserController(UserRepository ur){
		this.ur = ur;
	}
	
	@PostMapping
	public ResponseEntity<User> create(@RequestBody User user){
		
		
		long nameCount = ur.countByName(user.getName());
		long emailCount = ur.countByEmail(user.getEmail());
		
		if (emailCount != 0 || nameCount != 0) {
			throw new UserFieldsNotUniqueException(emailCount, nameCount);
		}
		
		User savedusr = ur.save(user);
		
		return new ResponseEntity<User>(savedusr, HttpStatus.CREATED);
	}
	
	@PostMapping(
			value = "/isunique",
			consumes = "application/json")
	public ResponseEntity<HashMap<String,String>> isUnique(@RequestBody HashMap<String,String> fields){
		
		long count = 0;
		
		for(Map.Entry<String, String> field : fields.entrySet()) {
			
			count = 0;
			
			switch (field.getKey()) {
			case "name" :
				count = ur.countByName(field.getValue());
				if(count == 0)
					field.setValue("true");
				else
					field.setValue("false");
			break;
			case "email" :
				count = ur.countByEmail(field.getValue());
				if(count == 0)
					field.setValue("true");
				else
					field.setValue("false");
			break;
			}
		}
		
		
		return new ResponseEntity<HashMap<String,String>>(fields, HttpStatus.OK) ;
				
	}
	
	@ExceptionHandler(UserFieldsNotUniqueException.class)
	public ResponseEntity<Error> userFieldsNotUnique(UserFieldsNotUniqueException e){
		
		String responseMsg = "FieldsNotUnique ";
		
		if(e.getEmailCount() != 0)
			responseMsg += "email ";
		
		if(e.getNameCount() != 0)
			responseMsg += "name";

		
		Error error = new Error(responseMsg);
		//TODO: The error contains a full stack trace which may need to be removed here perhaps with an empty trace
		
		return new ResponseEntity<Error>(error, HttpStatus.NOT_ACCEPTABLE);

	}
		
	

}