package com.haxwell.apps.quizki.repositories;

import java.util.Optional;	

import com.haxwell.apps.quizki.entities.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> { 
	Optional<UserRole> findByName(String name);
}
