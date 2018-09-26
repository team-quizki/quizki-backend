package com.haxwell.apps.quizki.services;

import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haxwell.apps.quizki.dtos.CreatedUserDTO;
import com.haxwell.apps.quizki.dtos.UserCreateDTO;
import com.haxwell.apps.quizki.repositories.UserRepository;
import com.haxwell.apps.quizki.repositories.UserRoleRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository uRepo;
	@Autowired
	private UserRoleRepository uroleRepo;
	@Autowired
	private ModelMapper mm;
	
	public UserServiceImpl(UserRepository uRepo, UserRoleRepository uroleRepo, ModelMapper mm) {
//		super();
		this.uRepo = uRepo;
		this.uroleRepo = uroleRepo;
		this.mm = mm;
	}
	
	
	public CreatedUserDTO  createNewUser(UserCreateDTO ucdto) {
		
	}
	
	
	public HashMap<String, String> emailOrNameIsUnique(HashMap<String, String> fields){
		
	}
	
}
