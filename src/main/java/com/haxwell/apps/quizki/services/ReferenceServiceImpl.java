package com.haxwell.apps.quizki.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haxwell.apps.quizki.entities.Reference;
import com.haxwell.apps.quizki.repositories.ReferenceRepository;

import net.minidev.json.JSONArray;

@Service
public class ReferenceServiceImpl implements ReferenceService {

	@Autowired
	private ReferenceRepository rr;
	
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

}
