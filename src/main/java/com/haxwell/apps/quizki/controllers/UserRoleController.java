package com.haxwell.apps.quizki.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haxwell.apps.quizki.entities.UserRole;
import com.haxwell.apps.quizki.repositories.UserRoleRepository;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping(value = {"/api/roles"})
public class UserRoleController {
	
	private final UserRoleRepository urp;
	private UserRole uRole;

	
	@Autowired
	UserRoleController(UserRoleRepository urp){
		this.urp = urp;
	}
	
	@GetMapping
	@ResponseBody
	public List<UserRole> readAll() {
		return  (List<UserRole>) this.urp.findAll();
		
	}
	
	
	
}
