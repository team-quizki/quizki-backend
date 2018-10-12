package com.haxwell.apps.quizki.repositories;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.haxwell.apps.quizki.entities.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
	Page<Question> findByUserId(long userId, Pageable pageable);

}
