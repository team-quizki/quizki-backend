package com.haxwell.apps.quizki.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haxwell.apps.quizki.dtos.CreatedUserDTO;
import com.haxwell.apps.quizki.dtos.UserCreateDTO;
import com.haxwell.apps.quizki.entities.User;
import com.haxwell.apps.quizki.entities.UserRole;
import com.haxwell.apps.quizki.exceptions.UserFieldsNotUniqueException;
import com.haxwell.apps.quizki.exceptions.UserRoleNotInDatabaseException;
import com.haxwell.apps.quizki.exceptions.ValidationErrorData;
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
		
		long nameCount = uRepo.countByName(ucdto.getName());
		long emailCount = uRepo.countByEmail(ucdto.getEmail());
		
		if (emailCount != 0 || nameCount != 0) {
			ValidationErrorData data = new ValidationErrorData();
			if(emailCount != 0)
				data.addFieldError("email", "email.not.unique");
			if(nameCount != 0)
				data.addFieldError("name", "name.not.unique");
			
			
			throw new UserFieldsNotUniqueException(data);
		}
		
		long role_id = ucdto.getRoleId();
		Optional<UserRole> urole = uroleRepo.findById(role_id);
		
		if(!urole.isPresent()) {
			ValidationErrorData data = new ValidationErrorData();
			data.addFieldError("role", "role.not.in.database");
			throw new UserRoleNotInDatabaseException(data);
		}
		
		//Create a new User instance and use mapper to populate it from the ucdto here
		
		
		User savedusr = uRepo.save(user);
		
		//then create an instance of CreatedUserDTO and use the mapper to populate it from the savedusr
		//and return the CreatedUserDTO
	}
	
	
	public HashMap<String, String> emailOrNameIsUnique(HashMap<String, String> fields){
		
		long count = 0;
		
		for(Map.Entry<String, String> field : fields.entrySet()) {
			
			count = 0;
			
			switch (field.getKey()) {
			case "name" :
				count = uRepo.countByName(field.getValue());
				if(count == 0)
					field.setValue("true");
				else
					field.setValue("false");
			break;
			case "email" :
				count = uRepo.countByEmail(field.getValue());
				if(count == 0)
					field.setValue("true");
				else
					field.setValue("false");
			break;
			}
		}
		
		return fields;
	}
	
}
