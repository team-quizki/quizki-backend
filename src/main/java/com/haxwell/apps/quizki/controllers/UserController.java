package com.haxwell.apps.quizki.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.haxwell.apps.quizki.dtos.CreatedUserDTO;
import com.haxwell.apps.quizki.dtos.UserCreateDTO;
import com.haxwell.apps.quizki.entities.User;
import com.haxwell.apps.quizki.entities.UserRole;
import com.haxwell.apps.quizki.exceptions.UserFieldsNotUniqueException;
import com.haxwell.apps.quizki.exceptions.UserRoleNotInDatabaseException;
import com.haxwell.apps.quizki.exceptions.ValidationErrorData;
import com.haxwell.apps.quizki.repositories.UserRepository;
import com.haxwell.apps.quizki.repositories.UserRoleRepository;
import com.haxwell.apps.quizki.services.UserService;

@CrossOrigin (origins = "http://localhost:4200")
@RestController
@RequestMapping ( value = {"/api/users"} )
public class UserController {
	
	private UserService uService;
	
	@Autowired
	UserController(UserService uService){
		this.uService = uService;
	}
	
	@PostMapping
	@ResponseBody
	public CreatedUserDTO create(@RequestBody @Valid UserCreateDTO userDTO ){

		return uService.createNewUser(userDTO);
	}
	
	@PostMapping(
			value = "/isUnique",
			consumes = "application/json")
	public ResponseEntity<HashMap<String,String>> isUnique(@RequestBody HashMap<String,String> fields){
		
		fields = uService.emailOrNameIsUnique(fields);
		
		
		return new ResponseEntity<HashMap<String,String>>(fields, HttpStatus.OK) ;
				
	}
		
	

}
