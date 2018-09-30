package com.haxwell.apps.quizki.services;

import java.util.List;

import com.haxwell.apps.quizki.entities.Topic;

public interface TopicService {

	public String /* JSON, of IDs */ createNew(List<Topic> list);
}
