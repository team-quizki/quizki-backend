package com.haxwell.apps.quizki.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.haxwell.apps.quizki.entities.User;
import com.haxwell.apps.quizki.repositories.UserRepository;

@CrossOrigin (origins = "http://localhost:4200")
@RestController
@RequestMapping ( value = {"/api/users"} )
public class UserController {
	
	private final UserRepository ur;
	
	UserController(UserRepository ur){
		this.ur = ur;
	}
	
	@PostMapping
	public ResponseEntity<User> create(@RequestBody User user){
		
		User savedusr = ur.save(user);
		
		return new ResponseEntity<User>(savedusr, HttpStatus.CREATED);
	}

}
