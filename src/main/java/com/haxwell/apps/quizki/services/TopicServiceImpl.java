package com.haxwell.apps.quizki.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.haxwell.apps.quizki.entities.Topic;
import com.haxwell.apps.quizki.repositories.TopicRepository;

import net.minidev.json.JSONArray;

@Service
public class TopicServiceImpl implements TopicService {

	@Autowired
	private TopicRepository tr;


	
	public TopicServiceImpl(TopicRepository tr) {
		this.tr = tr;
	}
	
	@Override
	public String createNew(List<Topic> list) {
		JSONArray rtn = new JSONArray(); 

		for (Topic t : list) {
			Topic existingTopic = tr.findByText(t.getText());

			if (existingTopic == null) {
				existingTopic = new Topic(t.getText());
				existingTopic = tr.save(existingTopic);
			}

			rtn.add(existingTopic.getId());
		}

		return rtn.toJSONString();
	}
	@Override
	public List<Topic> getTopicByText(String word, int page, int size) {

		List<Topic> topics;

		if (word == null || word == "") {
			topics = tr.findAll(PageRequest.of(page-1, size)).getContent();
		} else {
			topics = tr.findByText(word, PageRequest.of(page-1, size));
		}

		return topics;
	}

}
