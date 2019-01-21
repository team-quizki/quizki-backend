package com.haxwell.apps.quizki.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.haxwell.apps.quizki.entities.Topic;

public interface TopicService {

	public String /* JSON, of IDs */ createNew(List<Topic> list);

	public List<Topic> getTopicByText(String word, int page, int size);

}
