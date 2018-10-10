package com.haxwell.apps.quizki.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.haxwell.apps.quizki.dtos.CreatedUserDTO;
import com.haxwell.apps.quizki.dtos.UserCreateDTO;
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
	public ResponseEntity<HashMap<String,Boolean>> isUnique(@RequestBody HashMap<String,String> fields){
		
		//fields = uService.emailOrNameIsUnique(fields);
		
		return new ResponseEntity<HashMap<String,Boolean>>(uService.emailOrNameIsUnique(fields), HttpStatus.OK) ;
				
	}

}
