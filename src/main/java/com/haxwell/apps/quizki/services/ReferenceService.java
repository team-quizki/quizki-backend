package com.haxwell.apps.quizki.services;

import java.util.List;

import com.haxwell.apps.quizki.entities.Reference;

public interface ReferenceService {
	public String /* JSON, of IDs */ createNew(List<Reference> list);

	List<Reference> getReferenceByText(String word, int page, int size);
}
