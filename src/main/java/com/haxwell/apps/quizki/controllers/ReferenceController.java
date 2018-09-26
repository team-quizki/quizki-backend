package com.haxwell.apps.quizki.controllers;

import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haxwell.apps.quizki.repositories.ReferenceRepository;

import net.minidev.json.JSONArray;
import net.minidev.json.parser.JSONParser;

@CrossOrigin
@RestController
@RequestMapping(value = { "/api/reference" })
public class ReferenceController {

	private final ReferenceRepository rr;
	
	@Autowired
	ReferenceController(ReferenceRepository rr) {
		this.rr = rr;
	}

	@PostMapping(value = "/")
	public void createNew(HttpServletRequest request, Model model) throws Exception {
		
		String value = request.getParameter("value");
		
		JSONParser p = new JSONParser(JSONParser.MODE_STRICTEST);
		JSONArray arr = (JSONArray)p.parse(value);
		
		arr.forEach(new Consumer<Object>() {
			public void accept(Object o) {
				System.out.println(o.getClass().toGenericString());
			}
		});
		
	}
}