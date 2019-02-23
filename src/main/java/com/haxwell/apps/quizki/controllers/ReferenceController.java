package com.haxwell.apps.quizki.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.haxwell.apps.quizki.entities.Reference;
import com.haxwell.apps.quizki.entities.Topic;
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
	
	@GetMapping
	@ResponseBody
	public List<Reference> getReferenceByText(
			@RequestParam(value = "q", required = false)  String word,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {

		return rs.getReferenceByText(word, page, size);
	}
}