package com.haxwell.apps.quizki.repositories;

import org.springframework.data.repository.CrudRepository;

import com.haxwell.apps.quizki.entities.Topic;

public interface TopicRepository extends CrudRepository<Topic, Long> {
	Topic findByText(String txt);
}
