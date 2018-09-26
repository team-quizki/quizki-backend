package com.haxwell.apps.quizki.controllers;

import com.haxwell.apps.quizki.entities.Topic;

import java.util.function.Consumer;	

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haxwell.apps.quizki.repositories.TopicRepository;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

@CrossOrigin
@RestController
@RequestMapping(value = { "/api/topic" })
public class TopicController {

	private final TopicRepository tr;
	
	@Autowired
	TopicController(TopicRepository tr) {
		this.tr = tr;
	}

	@PostMapping(value = "/")
	public JSONArray createNew(HttpServletRequest request, Model model) throws Exception {
		
		String value = request.getParameter("value");
		
		JSONParser p = new JSONParser(JSONParser.MODE_STRICTEST);
		JSONObject obj = (JSONObject)p.parse(value);
		
		JSONArray arr = (JSONArray)obj.get("arr");
		JSONArray rtn = new JSONArray();
		
		arr.forEach(new Consumer<Object>() {
			public void accept(Object o) {
				Topic topic = tr.findByText(o.toString());
							
				if (topic == null) {
					topic = new Topic(o.toString());
					topic = tr.save(topic);
				}
				
				rtn.add(topic.getId());
			}
		});
		
		return rtn;
	}
}