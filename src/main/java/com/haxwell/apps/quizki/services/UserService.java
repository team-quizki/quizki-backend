package com.haxwell.apps.quizki.services;

import java.util.HashMap;

import com.haxwell.apps.quizki.dtos.CreatedUserDTO;
import com.haxwell.apps.quizki.dtos.UserCreateDTO;

public interface UserService {
	
	public CreatedUserDTO  createNewUser(UserCreateDTO ucdto);
	public HashMap<String, Boolean> emailOrNameIsUnique(HashMap<String, String> fields);

}
