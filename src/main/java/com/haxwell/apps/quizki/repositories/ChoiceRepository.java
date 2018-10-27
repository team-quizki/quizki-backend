package com.haxwell.apps.quizki.repositories;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.haxwell.apps.quizki.entities.Choice;

@Repository
public interface ChoiceRepository extends CrudRepository<Choice, Long> {
	Set<Choice> findByQuestionId(Long questionId);
}
