package com.haxwell.apps.quizki.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.haxwell.apps.quizki.entities.Reference;
import com.haxwell.apps.quizki.entities.Topic;
import com.haxwell.apps.quizki.repositories.ReferenceRepository;

import net.minidev.json.JSONArray;

@Service
public class ReferenceServiceImpl implements ReferenceService {

	@Autowired
	private ReferenceRepository rr;
	
	
	public ReferenceServiceImpl(ReferenceRepository rr) {
		this.rr = rr;
	}
	
	@Override
	public String createNew(List<Reference> list) {
		JSONArray rtn = new JSONArray(); 

		for (Reference r : list) {
			Reference existingRef = rr.findByText(r.getText());

			if (existingRef == null) {
				existingRef = new Reference(r.getText());
				existingRef = rr.save(existingRef);
			}

			rtn.add(existingRef.getId());
		}

		return rtn.toJSONString();
	}
	
	@Override
	public List<Reference> getReferenceByText(String word, int page, int size) {

		List<Reference> references;

		if (word == null || word == "") {
			references = rr.findAll(PageRequest.of(page-1, size)).getContent();
		} else {
			references = rr.findByText(word, PageRequest.of(page-1, size));
		}

		return references;
	}

}
