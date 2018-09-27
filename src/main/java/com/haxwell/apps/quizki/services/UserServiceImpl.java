package com.haxwell.apps.quizki.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


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
	
	
	public UserServiceImpl(UserRepository uRepo, UserRoleRepository uroleRepo) {

		this.uRepo = uRepo;
		this.uroleRepo = uroleRepo;
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
		
		//Create a new User instance and map it from the ucdto DTO here
		User user = new User();
		
		user.setName(ucdto.getName());
		user.setEmail(ucdto.getEmail());
		user.setRole(urole.get());
		user.setDemographic("default");				//TODO remove
		user.setEnabled(1);							//TODO remove
		user.setFullname(ucdto.getFullname());
		user.setPassword(ucdto.getPassword());
		user.setId(null);
		
		User savedusr = uRepo.save(user);
		
		//then create an instance of CreatedUserDTO and map it from the savedusr
		//and return the CreatedUserDTO
		
		CreatedUserDTO cudto = new CreatedUserDTO();
		
		cudto.setEmail(savedusr.getEmail());
		cudto.setName(savedusr.getName());
		cudto.setFullname(savedusr.getFullname());
		cudto.setId(savedusr.getId());
		cudto.setRoleId(savedusr.getRole().getId());
		
		return cudto;
		
		
		
	}
	
	
	public HashMap<String, Boolean> emailOrNameIsUnique(HashMap<String, String> fields){
		
		long count = 0;
		HashMap<String, Boolean> rtn = new HashMap<String, Boolean>();
		
		for(Map.Entry<String, String> field : fields.entrySet()) {
			
			count = 0;
			
			switch (field.getKey()) {
			case "name" :
				count = uRepo.countByName(field.getValue());


				rtn.put("name", count == 0);

			break;
			case "email" :
				count = uRepo.countByEmail(field.getValue());
				
				rtn.put("email", count == 0);

			break;
			}
		}
		
		return rtn;
	}
	
}
