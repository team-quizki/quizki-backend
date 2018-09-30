package com.haxwell.apps.quizki.repositories;

import org.springframework.data.repository.CrudRepository;

import com.haxwell.apps.quizki.entities.Reference;
import com.haxwell.apps.quizki.entities.Topic;

public interface ReferenceRepository extends CrudRepository<Reference, Long> {
	Reference findByText(String txt);
}
