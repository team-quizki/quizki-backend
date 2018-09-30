package com.haxwell.apps.quizki.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haxwell.apps.quizki.entities.Reference;
import com.haxwell.apps.quizki.services.ReferenceService;

@CrossOrigin
@RestController
@RequestMapping(value = { "/api/reference" })
public class ReferenceController {

	@Autowired
	ReferenceService rs;
	
	@Autowired
	ReferenceController() {

	}

	// SAMPLE INPUT: [{"text": "one"}, {"text": "two"}, {"text": "three"}, {"text": "four"}]
	@PostMapping(value = "/", consumes = "application/json", produces = "application/json")
	public String createNew(@RequestBody List<Reference> refs) throws Exception {
		return rs.createNew(refs);
	}
}