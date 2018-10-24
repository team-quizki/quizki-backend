package com.haxwell.apps.quizki.repositories;

import java.awt.print.Pageable;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.haxwell.apps.quizki.entities.Question;

//TODO: implement pagination here and in the service layer

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
	Set<Question> findByUserId(long userId);

}
