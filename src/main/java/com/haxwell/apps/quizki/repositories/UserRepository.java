package com.haxwell.apps.quizki.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.haxwell.apps.quizki.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByName(String name);
	Optional<User> findByEmail(String email);
	long countByName(String name);
	long countByEmail(String email);
}
